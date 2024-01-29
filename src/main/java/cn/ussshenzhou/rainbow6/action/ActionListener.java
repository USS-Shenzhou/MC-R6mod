package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
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
import java.util.List;

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
        //TODO update
        /*if (event.side == LogicalSide.SERVER) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        if (event.side != LogicalSide.CLIENT) {
            return;
        }
        Player player = event.player;
        AnimationCapability animationCapability = AnimationCapability.get(player);
        if (animationCapability == null) {
            return;
        }
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }
        animationCapability.tick(player, actionCapability);*/
    }

    /*@SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }
        List<Action> actions = actionCapability.getActions();
        boolean needSync = event.side == LogicalSide.CLIENT && player.isLocalPlayer();
        SyncActionPacket.Encoder builder = SyncActionPacket.Encoder.reset();
        for (Action action : actions) {
            if (needSync) {
                bufferOfPreState.clear();
                action.saveSynchronizedState(bufferOfPreState);
                bufferOfPreState.flip();
            }
            if (action.isDoing()) {
                action.setDoingTick(action.getDoingTick() + 1);
                action.setNotDoingTick(0);
            } else {
                action.setDoingTick(0);
                action.setNotDoingTick(action.getNotDoingTick() + 1);
            }

            action.onTick(player, actionCapability);
            if (event.side == LogicalSide.CLIENT) {
                action.onClientTick(player, actionCapability);
            } else {
                action.onServerTick(player, actionCapability);
            }

            if (player.isLocalPlayer()) {
                if (action.isDoing()) {
                    boolean canContinue = action.canContinueInClient(player, actionCapability);
                    if (!canContinue) {
                        action.setDoing(false);
                        action.onStopInLocalClient(player);
                        action.onStop(player);
                        builder.appendFinishMsg(actionCapability, action);
                    }
                } else {
                    bufferOfStarting.clear();
                    boolean start = action.canStartInClient(player, actionCapability, bufferOfStarting);
                    bufferOfStarting.flip();
                    if (start) {
                        action.setDoing(true);
                        action.onStartInLocalClient(player, actionCapability, bufferOfStarting);
                        bufferOfStarting.rewind();
                        action.onStart(player, actionCapability);
                        builder.appendStartData(actionCapability, action, bufferOfStarting);
                    }
                }
            }

            if (action.isDoing()) {
                action.onWorkingTick(player, actionCapability);
                if (event.side == LogicalSide.CLIENT) {
                    action.onWorkingTickInClient(player, actionCapability);
                    if (player.isLocalPlayer()) {
                        action.onWorkingTickInLocalClient(player, actionCapability);
                    }
                } else {
                    action.onWorkingTickInServer(player, actionCapability);
                }
            }

            if (needSync) {
                bufferOfPostState.clear();
                action.saveSynchronizedState(bufferOfPostState);
                bufferOfPostState.flip();

                if (bufferOfPostState.limit() == bufferOfPreState.limit()) {
                    while (bufferOfPreState.hasRemaining()) {
                        if (bufferOfPostState.get() != bufferOfPreState.get()) {
                            bufferOfPostState.rewind();
                            builder.appendSyncData(actionCapability, action, bufferOfPostState);
                            break;
                        }
                    }
                } else {
                    bufferOfPostState.rewind();
                    builder.appendSyncData(actionCapability, action, bufferOfPostState);
                }
            }
        }
        if (needSync) {
            SyncHelper.syncActionFromClient(player, builder);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        Player clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer == null) {
            return;
        }
        for (Player player : clientPlayer.level().players()) {
            ActionCapability actionCapability = ActionCapability.get(player);
            if (actionCapability == null) {
                return;
            }
            List<Action> actions = actionCapability.getActions();
            for (Action action : actions) {
                action.onRenderTick(event, player, actionCapability);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onViewRender(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }
        AnimationCapability animationCapability = AnimationCapability.get(player);
        if (animationCapability == null) {
            return;
        }
        animationCapability.cameraSetup(event, player, actionCapability);
    }*/
}
