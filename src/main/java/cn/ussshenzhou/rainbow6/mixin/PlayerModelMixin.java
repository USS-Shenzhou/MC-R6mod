package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.dataattachment.AnimationData;
import cn.ussshenzhou.rainbow6.client.animationplayer.PlayerModelTransformer;
import cn.ussshenzhou.rainbow6.dataattachment.DataUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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
    @Unique
    private PlayerModelTransformer r6msTransformer = null;

    public PlayerModelMixin(ModelPart pRoot) {
        super(pRoot);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    protected void onSetupAnimHead(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (!(entity instanceof Player player)) {
            return;
        }
        PlayerModel<?> model = (PlayerModel<?>) (Object) this;
        r6msTransformer = new PlayerModelTransformer(
                player,
                model,
                slim,
                ageInTicks,
                limbSwing,
                limbSwingAmount,
                netHeadYaw,
                headPitch
        );
        r6msTransformer.reset();

        AnimationData animation = DataUtils.getAnimationData(player);
        boolean shouldCancel = animation.animatePre(player, r6msTransformer);
        r6msTransformer.copyFromBodyToWear();
        if (shouldCancel) {
            r6msTransformer = null;
            info.cancel();
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    protected void onSetupAnimTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
        if (!(entity instanceof Player player)) {
            return;
        }
        AnimationData animation = DataUtils.getAnimationData(player);

        if (r6msTransformer != null) {
            animation.animatePost(player, r6msTransformer);
            r6msTransformer.copyFromBodyToWear();
            r6msTransformer = null;
        }
    }

}
