package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.dataattachment.*;
import cn.ussshenzhou.rainbow6.network.SyncActionPacket;
import cn.ussshenzhou.rainbow6.network.SyncHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.TickEvent;

import java.nio.ByteBuffer;

/**
 * This file is copied and modified from com.alrex.parcool.common.action.ActionProcessor under GPLv3.
 *
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ActionListener {
    private static final ByteBuffer bufferOfPostState = ByteBuffer.allocate(128);
    private static final ByteBuffer bufferOfPreState = ByteBuffer.allocate(128);
    private static final ByteBuffer bufferOfStarting = ByteBuffer.allocate(128);

    @SubscribeEvent
    public static void animationCapabilityClientTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER || event.phase == TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        AnimationData animationData = DataUtils.getAnimationData(player);
        ActionData actionData = DataUtils.getActionData(player);
        animationData.tick(player, actionData);
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        ActionData actionData = DataUtils.getActionData(player);
        boolean needSync = event.side == LogicalSide.CLIENT && player.isLocalPlayer();
        SyncActionPacket.Encoder builder = SyncActionPacket.Encoder.reset();
        for (BaseAction baseAction : actionData.actions) {
            if (needSync) {
                bufferOfPreState.clear();
                baseAction.saveSynchronizedState(bufferOfPreState);
                bufferOfPreState.flip();
            }
            if (baseAction.isDoing()) {
                baseAction.setDoingTick(baseAction.getDoingTick() + 1);
                baseAction.setNotDoingTick(0);
            } else {
                baseAction.setDoingTick(0);
                baseAction.setNotDoingTick(baseAction.getNotDoingTick() + 1);
            }

            baseAction.onTick(player, actionData);
            if (event.side == LogicalSide.CLIENT) {
                baseAction.onClientTick(player, actionData);
            } else {
                baseAction.onServerTick(player, actionData);
            }

            if (player.isLocalPlayer()) {
                if (baseAction.isDoing()) {
                    boolean canContinue = baseAction.canContinue(player, actionData);
                    if (!canContinue) {
                        baseAction.setDoing(false);
                        baseAction.onStopInLocalClient(player);
                        baseAction.onStop(player);
                        builder.appendFinishMsg(actionData, baseAction);
                    }
                } else {
                    bufferOfStarting.clear();
                    boolean start = baseAction.canStart(player, actionData, bufferOfStarting);
                    bufferOfStarting.flip();
                    if (start) {
                        baseAction.setDoing(true);
                        baseAction.onStartInLocalClient(player, actionData, bufferOfStarting);
                        bufferOfStarting.rewind();
                        baseAction.onStart(player, actionData);
                        builder.appendStartData(actionData, baseAction, bufferOfStarting);
                    }
                }
            }

            if (baseAction.isDoing()) {
                baseAction.onWorkingTick(player, actionData);
                if (event.side == LogicalSide.CLIENT) {
                    baseAction.onWorkingTickInClient(player, actionData);
                    if (player.isLocalPlayer()) {
                        baseAction.onWorkingTickInLocalClient(player, actionData);
                    }
                } else {
                    baseAction.onWorkingTickInServer(player, actionData);
                }
            }

            if (needSync) {
                bufferOfPostState.clear();
                baseAction.saveSynchronizedState(bufferOfPostState);
                bufferOfPostState.flip();

                if (bufferOfPostState.limit() == bufferOfPreState.limit()) {
                    while (bufferOfPreState.hasRemaining()) {
                        if (bufferOfPostState.get() != bufferOfPreState.get()) {
                            bufferOfPostState.rewind();
                            builder.appendSyncData(actionData, baseAction, bufferOfPostState);
                            break;
                        }
                    }
                } else {
                    bufferOfPostState.rewind();
                    builder.appendSyncData(actionData, baseAction, bufferOfPostState);
                }
            }
        }
        if (needSync) {
            SyncHelper.syncActionFromClient(player, builder);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        Player clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer == null) {
            return;
        }
        for (Player player : clientPlayer.level().players()) {
            ActionData actionData = DataUtils.getActionData(player);
            for (BaseAction baseAction : actionData.actions) {
                baseAction.onRenderTick(event, player, actionData);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onViewRender(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ActionData actionData = DataUtils.getActionData(player);
        AnimationData animationData = DataUtils.getAnimationData(player);
        animationData.cameraSetup(event, player, actionData);
    }
}
