package cn.ussshenzhou.rainbow6.mixin;


import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
import cn.ussshenzhou.rainbow6.capability.ModCapabilities;
import cn.ussshenzhou.rainbow6.client.animationplayer.PlayerModelRotator;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author USS_Shenzhou
 */
@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void r6msCancelRenderPlayerWhenGetMapTopView(AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (!ClientMatch.isRenderPlayer()) {
            ci.cancel();
        }
    }

    /**
     * This method is copied and modified com.alrex.parcool.mixin.client.PlayerRendererMixin#onSetupRotations under GPLv3.
     */
    @Inject(method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V", at = @At("HEAD"))
    protected void onSetupRotations(AbstractClientPlayer player, PoseStack stack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci) {
        AnimationCapability animation = player.getCapability(ModCapabilities.ANIMATION_CAPABILITY);
        if (animation == null) {
            return;
        }
        PlayerModelRotator rotator = new PlayerModelRotator(stack, player, Minecraft.getInstance().getFrameTime());
        animation.applyRotate(player, rotator);
    }
}
