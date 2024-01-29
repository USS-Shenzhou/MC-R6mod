package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
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
public abstract class Action {
    private boolean doing = false;
    private int doingTick = 0;
    private int notDoingTick = 0;
    private final Actions actionEnum;

    public Action(Actions actionEnum) {
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
    public abstract boolean canStart(Player player, ActionCapability capability, ByteBuffer startInfo);

    @OnlyIn(Dist.CLIENT)
    public abstract boolean canContinue(Player player, ActionCapability capability);

    public void onStart(Player player, ActionCapability capability) {
    }

    public void onStartInServer(Player player, ActionCapability capability, ByteBuffer startInfo) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onStartInOtherClient(Player player, ActionCapability capability, ByteBuffer startInfo) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onStartInLocalClient(Player player, ActionCapability capability, ByteBuffer startInfo) {
    }

    public void onStop(Player player) {
    }

    public void onStopInServer(Player player) {
    }

    public void onStopInOtherClient(Player player) {
    }

    public void onStopInLocalClient(Player player) {
    }

    public void onWorkingTick(Player player, ActionCapability capability) {
    }

    public void onWorkingTickInServer(Player player, ActionCapability capability) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onWorkingTickInClient(Player player, ActionCapability capability) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onWorkingTickInLocalClient(Player player, ActionCapability capability) {
    }

    public void onTick(Player player, ActionCapability capability) {
    }

    public void onServerTick(Player player, ActionCapability capability) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onClientTick(Player player, ActionCapability capability) {
    }

    @OnlyIn(Dist.CLIENT)
    public void onRenderTick(TickEvent.RenderTickEvent event, Player player, ActionCapability capability) {
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
