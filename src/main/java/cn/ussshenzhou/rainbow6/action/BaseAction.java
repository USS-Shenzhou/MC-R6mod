package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.dataattachment.ActionData;
import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.t88.config.ConfigHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.TickEvent;

import java.nio.ByteBuffer;

/**
 * This file is copied and modified from com.alrex.parcool.common.action.Action under GPLv3.
 *
 * @author USS_Shenzhou
 */
public abstract class BaseAction {
    private boolean doing = false;
    private int doingTick = 0;
    private int notDoingTick = 0;
    private final Actions actionEnum;

    public BaseAction(Actions actionEnum) {
        this.actionEnum = actionEnum;
    }

    public Actions getActionEnum() {
        return actionEnum;
    }

    public void setDoingTick(int doingTick) {
        this.doingTick = doingTick;
    }

    public void setNotDoingTick(int notDoingTick) {
        this.notDoingTick = notDoingTick;
    }

    public int getDoingTick() {
        return doingTick;
    }

    public int getNotDoingTick() {
        return notDoingTick;
    }

    public boolean isDoing() {
        return doing;
    }

    public void setDoing(boolean value) {
        doing = value;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract boolean canStart(Player player, ActionData actionData, ByteBuffer startInfo);

    @OnlyIn(Dist.CLIENT)
    public abstract boolean canContinue(Player player, ActionData actionData);

    public void onStart(Player player, ActionData actionData) {
    }

    public void onStartInServer(Player player, ActionData actionData, ByteBuffer startInfo) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onStartInOtherClient(Player player, ActionData actionData, ByteBuffer startInfo) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onStartInLocalClient(Player player, ActionData actionData, ByteBuffer startInfo) {
    }

    public void onStop(Player player) {
    }

    public void onStopInServer(Player player) {
    }

    public void onStopInOtherClient(Player player) {
    }

    public void onStopInLocalClient(Player player) {
    }

    public void onWorkingTick(Player player, ActionData actionData) {
    }

    public void onWorkingTickInServer(Player player, ActionData actionData) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onWorkingTickInClient(Player player, ActionData actionData) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onWorkingTickInLocalClient(Player player, ActionData actionData) {
    }

    public void onTick(Player player, ActionData actionData) {
    }

    public void onServerTick(Player player, ActionData actionData) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onClientTick(Player player, ActionData actionData) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onRenderTick(TickEvent.RenderTickEvent event, Player player, ActionData actionData) {
    }

    public void restoreSynchronizedState(ByteBuffer buffer) {
    }

    public void saveSynchronizedState(ByteBuffer buffer) {
    }

    protected static boolean canContinue(KeyTrig keyTrig, boolean isDown, boolean isPressed) {
        return keyTrig == KeyTrig.HOLD ? isDown : !isPressed;
    }

    protected static Control getConfig(){
        return ConfigHelper.getConfigRead(Control.class);
    }
}
