package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.client.animationplayer.Animator;
import cn.ussshenzhou.rainbow6.client.animationplayer.PlayerModelRotator;
import cn.ussshenzhou.rainbow6.client.animationplayer.PlayerModelTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ViewportEvent;

/**
 * This file is copied and modified from com.alrex.parcool.common.capability.impl.Animation under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class AnimationCapability {

    private Animator animator = null;

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public boolean animatePre(Player player, PlayerModelTransformer modelTransformer) {
        if (animator == null) {
            return false;
        }
        ActionCapability actionCapability = player.getCapability(ModCapabilities.ACTION_CAPABILITY);
        return animator.animatePre(player, actionCapability, modelTransformer);
    }

    public void animatePost(Player player, PlayerModelTransformer modelTransformer) {
        ActionCapability actionCapability = player.getCapability(ModCapabilities.ACTION_CAPABILITY);
        if (actionCapability == null) {
            return;
        }
        if (animator == null) {
            return;
        }
        animator.animatePost(player, actionCapability, modelTransformer);
    }

    public void applyRotate(AbstractClientPlayer player, PlayerModelRotator rotator) {
        ActionCapability actionCapability = player.getCapability(ModCapabilities.ACTION_CAPABILITY);
        if (actionCapability == null) {
            return;
        }
        if (animator == null) {
            return;
        }
        animator.rotate(player, actionCapability, rotator);
    }

    public void cameraSetup(ViewportEvent.ComputeCameraAngles event, Player player, ActionCapability actionCapability) {
        if (animator == null) {
            return;
        }
        if (player.isLocalPlayer() && Minecraft.getInstance().options.getCameraType().isFirstPerson()
        ) {
            return;
        }
        animator.onCameraSetUp(event, player, actionCapability);
    }

    public void tick(Player player, ActionCapability actionCapability) {
        if (animator != null) {
            animator.tick();
            if (animator.shouldRemoved(player, actionCapability)) {
                animator = null;
            }
        }
    }

    public boolean hasAnimator() {
        return animator != null;
    }

    public void removeAnimator() {
        animator = null;
    }
}
