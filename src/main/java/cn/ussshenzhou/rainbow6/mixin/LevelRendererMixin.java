package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.mixinproxy.LevelRendererProxy;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author USS_Shenzhou
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin implements LevelRendererProxy {
    private float cameraZoomFactor;
    private boolean enableOrthographic = false;
    private boolean clipRoof = false;

    public LevelRendererProxy enableOrthographic(float cameraZoomFactor1) {
        cameraZoomFactor = cameraZoomFactor1;
        enableOrthographic = true;
        clipRoof = false;
        return this;
    }

    public void setClipRoof(boolean clipRoof) {
        this.clipRoof = clipRoof;
    }

    public void disableOrthographic() {
        enableOrthographic = false;
    }

    @ModifyVariable(method = "renderLevel", at = @At("HEAD"), argsOnly = true)
    private Matrix4f modifyMatrix4fR6ms(Matrix4f m) {
        if (enableOrthographic) {
            Window window = Minecraft.getInstance().getWindow();
            float width = cameraZoomFactor * window.getWidth() / window.getHeight();
            float height = cameraZoomFactor;
            //80 - 108
            //40 - 216
            //20 - 432
            // x * y = 8640
            Matrix4f matrix4f = Matrix4f.orthographic(-width, width, height, -height, clipRoof ? 0 : -9999, 9999);
            RenderSystem.setProjectionMatrix(matrix4f);
            return matrix4f;
        } else {
            return m;
        }
    }

    @ModifyVariable(method = "prepareCullFrustum", at = @At("HEAD"), argsOnly = true)
    private Matrix4f modifyMatrix4fR6ms1(Matrix4f m) {
        if (enableOrthographic) {
            Window window = Minecraft.getInstance().getWindow();
            float width = cameraZoomFactor * window.getWidth() / window.getHeight();
            float height = cameraZoomFactor;
            return Matrix4f.orthographic(-width, width, height, -height, clipRoof ? 0 : -9999, 9999);
        } else {
            return m;
        }
    }
}
