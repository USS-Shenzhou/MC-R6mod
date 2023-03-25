package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
import cn.ussshenzhou.rainbow6.client.animation.PlayerModelTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This file is copied and modified com.alrex.parcool.mixin.client.PlayerModelMixin under GPLv3.
 *
 * @author USS_Shenzhou
 */
@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {
    @Shadow
    @Final
    private boolean slim;
    private PlayerModelTransformer transformer = null;

    public PlayerModelMixin(ModelPart pRoot) {
        super(pRoot);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    protected void onSetupAnimHead(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (!(entity instanceof Player player)) {
            return;
        }
        PlayerModel<?> model = (PlayerModel<?>) (Object) this;
        transformer = new PlayerModelTransformer(
                player,
                model,
                slim,
                ageInTicks,
                limbSwing,
                limbSwingAmount,
                netHeadYaw,
                headPitch
        );
        transformer.reset();

        AnimationCapability animation = AnimationCapability.get(player);
        if (animation == null) {
            return;
        }
        boolean shouldCancel = animation.animatePre(player, transformer);
        transformer.copyFromBodyToWear();
        if (shouldCancel) {
            transformer = null;
            info.cancel();
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    protected void onSetupAnimTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (!(entity instanceof Player player)) {
            return;
        }
        AnimationCapability animation = AnimationCapability.get(player);
        if (animation == null) {
            transformer = null;
            return;
        }

        if (transformer != null) {
            animation.animatePost(player, transformer);
            transformer.copyFromBodyToWear();
            transformer = null;
        }
    }

}
