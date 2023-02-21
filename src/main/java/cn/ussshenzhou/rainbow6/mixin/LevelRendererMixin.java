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
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Mixin(LevelRenderer.class)
public class LevelRendererMixin implements LevelRendererProxy {
    private float cameraZoomFactor$r6ms;
    private boolean enableOrthographic$r6ms = false;
    private boolean clipRoof$r6ms = false;

    public LevelRendererProxy enableOrthographic(float cameraZoomFactor1) {
        cameraZoomFactor$r6ms = cameraZoomFactor1;
        enableOrthographic$r6ms = true;
        clipRoof$r6ms = false;
        return this;
    }

    public void setClipRoof$r6ms(boolean clipRoof$r6ms) {
        this.clipRoof$r6ms = clipRoof$r6ms;
    }

    public void disableOrthographic() {
        enableOrthographic$r6ms = false;
    }

    @ModifyVariable(method = "renderLevel", at = @At("HEAD"), argsOnly = true)
    private Matrix4f modifyMatrix4fR6ms(Matrix4f m) {
        if (enableOrthographic$r6ms) {
            Window window = Minecraft.getInstance().getWindow();
            float width = cameraZoomFactor$r6ms * window.getWidth() / window.getHeight();
            float height = cameraZoomFactor$r6ms;
            //80 - 108
            //40 - 216
            //20 - 432
            // x * y = 8640
            Matrix4f matrix4f = Matrix4f.orthographic(-width, width, height, -height, clipRoof$r6ms ? 0 : -9999, 9999);
            RenderSystem.setProjectionMatrix(matrix4f);
            return matrix4f;
        } else {
            return m;
        }
    }

    @ModifyVariable(method = "prepareCullFrustum", at = @At("HEAD"), argsOnly = true)
    private Matrix4f modifyMatrix4fR6ms1(Matrix4f m) {
        if (enableOrthographic$r6ms) {
            Window window = Minecraft.getInstance().getWindow();
            float width = cameraZoomFactor$r6ms * window.getWidth() / window.getHeight();
            float height = cameraZoomFactor$r6ms;
            return Matrix4f.orthographic(-width, width, height, -height, clipRoof$r6ms ? 0 : -9999, 9999);
        } else {
            return m;
        }
    }
}
