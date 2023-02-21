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
    private boolean needScreenShot$r6ms = false;
    private volatile NativeImage screenShot$r6ms = null;

    @Shadow
    private boolean renderHand;

    public void needScreenShot() {
        Minecraft.getInstance().execute(()->{
            needScreenShot$r6ms = true;
            renderHand = false;
        });
    }

    public NativeImage getScreenShot$r6ms() {
        return screenShot$r6ms;
    }

    public void clearScreenShot(){
        Minecraft.getInstance().execute(()->{
            screenShot$r6ms = null;
            renderHand = true;
        });
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", shift = At.Shift.AFTER))
    private void getScreenShot(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (needScreenShot$r6ms) {
            screenShot$r6ms = Screenshot.takeScreenshot(Minecraft.getInstance().getMainRenderTarget());
            needScreenShot$r6ms = false;
        }
    }
}
