package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.mixinproxy.GameRendererProxy;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Mixin(GameRenderer.class)
public class GameRendererMixin implements GameRendererProxy {
    private boolean r6msNeedScreenShot = false;
    private volatile NativeImage r6msScreenShot = null;

    @Shadow
    private boolean renderHand;

    @Override
    public void needScreenShot() {
        Minecraft.getInstance().execute(()->{
            r6msNeedScreenShot = true;
            renderHand = false;
        });
    }

    @Override
    public NativeImage getR6msScreenShot() {
        return r6msScreenShot;
    }

    @Override
    public void clearScreenShot(){
        Minecraft.getInstance().execute(()->{
            r6msScreenShot = null;
            renderHand = true;
        });
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", shift = At.Shift.AFTER))
    private void getScreenShot(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (r6msNeedScreenShot) {
            r6msScreenShot = Screenshot.takeScreenshot(Minecraft.getInstance().getMainRenderTarget());
            r6msNeedScreenShot = false;
        }
    }
}
