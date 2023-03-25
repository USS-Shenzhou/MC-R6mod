package cn.ussshenzhou.rainbow6.client.animation;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent;

;

/**
 * This file is copied and modified from com.alrex.parcool.client.animation.Animator under GPLv3.
 *
 * @author USS_Shenzhou
 */
public abstract class Animator {
    private int tick = 0;

    public final void tick() {
        tick++;
    }

    protected int getTick() {
        return tick;
    }

    public abstract boolean shouldRemoved(Player player, ActionCapability actionCapability);

    /**
     * @return You should return true if you want to cancel vanilla animation to control all about rendering
     */
    public boolean animatePre(
            Player player,
            ActionCapability actionCapability,
            PlayerModelTransformer transformer
    ) {
        return false;
    }

    /**
     * Called after vanilla animation is done
     * You can utilize this to use partially vanilla animation
     */
    public void animatePost(
            Player player,
            ActionCapability actionCapability,
            PlayerModelTransformer transformer
    ) {
    }

    public void rotate(
            Player player,
            ActionCapability actionCapability,
            PlayerModelRotator rotator
    ) {
    }

    public void onCameraSetUp(
            ViewportEvent.ComputeCameraAngles event,
            Player clientPlayer,
            ActionCapability actionCapability
    ) {
    }
}
