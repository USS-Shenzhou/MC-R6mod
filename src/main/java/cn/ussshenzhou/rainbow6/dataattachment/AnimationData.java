package cn.ussshenzhou.rainbow6.dataattachment;

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
public class AnimationData {

    private Animator animator = null;

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public boolean animatePre(Player player, PlayerModelTransformer modelTransformer) {
        if (animator == null) {
            return false;
        }
        return animator.animatePre(player, DataUtils.getActionData(player), modelTransformer);
    }

    public void animatePost(Player player, PlayerModelTransformer modelTransformer) {
        ActionData actionData = DataUtils.getActionData(player);
        if (animator == null) {
            return;
        }
        animator.animatePost(player, actionData, modelTransformer);
    }

    public void applyRotate(AbstractClientPlayer player, PlayerModelRotator rotator) {
        ActionData actionData = DataUtils.getActionData(player);
        if (animator == null) {
            return;
        }
        animator.rotate(player, actionData, rotator);
    }

    public void cameraSetup(ViewportEvent.ComputeCameraAngles event, Player player, ActionData actionData) {
        if (animator == null) {
            return;
        }
        if (player.isLocalPlayer() && Minecraft.getInstance().options.getCameraType().isFirstPerson()
        ) {
            return;
        }
        animator.onCameraSetUp(event, player, actionData);
    }

    public void tick(Player player, ActionData actionData) {
        if (animator != null) {
            animator.tick();
            if (animator.shouldRemoved(player, actionData)) {
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
