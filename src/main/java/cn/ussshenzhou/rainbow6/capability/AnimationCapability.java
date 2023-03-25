package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.client.animation.Animator;
import cn.ussshenzhou.rainbow6.client.animation.PlayerModelRotator;
import cn.ussshenzhou.rainbow6.client.animation.PlayerModelTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * This file is copied and modified from com.alrex.parcool.common.capability.impl.Animation under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class AnimationCapability {

    public static @Nullable AnimationCapability get(Player player) {
        LazyOptional<AnimationCapability> optional = player.getCapability(ModCapabilityRegistry.ANIMATION_CAPABILITY);
        return optional.orElse(null);
    }

    private Animator animator = null;

    public void setAnimator(Animator animator) {
        this.animator = animator;
    }

    public boolean animatePre(Player player, PlayerModelTransformer modelTransformer) {
        if (animator == null) {
            return false;
        }
        ActionCapability actionCapability = ActionCapability.get(player);
        return animator.animatePre(player, actionCapability, modelTransformer);
    }

    public void animatePost(Player player, PlayerModelTransformer modelTransformer) {
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }
        if (animator == null) {
            return;
        }
        animator.animatePost(player, actionCapability, modelTransformer);
    }

    public void applyRotate(AbstractClientPlayer player, PlayerModelRotator rotator) {
        ActionCapability actionCapability = ActionCapability.get(player);
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

    public static class Provider implements ICapabilityProvider {
        private final AnimationCapability instance = new AnimationCapability();
        private final LazyOptional<AnimationCapability> optional = LazyOptional.of(() -> instance);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilityRegistry.ANIMATION_CAPABILITY.orEmpty(cap, optional);
        }
    }
}
