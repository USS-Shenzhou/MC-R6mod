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
@Mixin(GameRenderer.class)
public class GameRendererMixin implements GameRendererProxy {
    private boolean needScreenShot = false;
    private volatile NativeImage screenShot = null;

    @Shadow
    boolean renderHand;

    public void needScreenShot() {
        Minecraft.getInstance().execute(()->{
            needScreenShot = true;
            renderHand = false;
        });
    }

    public NativeImage getScreenShot() {
        return screenShot;
    }

    public void clearScreenShot(){
        Minecraft.getInstance().execute(()->{
            screenShot = null;
            renderHand = true;
        });
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", shift = At.Shift.AFTER))
    private void getScreenShot(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (needScreenShot) {
            screenShot = Screenshot.takeScreenshot(Minecraft.getInstance().getMainRenderTarget());
            needScreenShot = false;
        }
    }
}
