package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.mixinproxy.LevelRendererProxy;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Mixin(LevelRenderer.class)
public class LevelRendererMixin implements LevelRendererProxy {
    private float r6msCameraZoomFactor;
    private boolean r6msEnableOrthographic = false;
    private boolean r6msClipRoof = false;

    @Override
    public LevelRendererProxy r6msEnableOrthographic(float cameraZoomFactor1) {
        r6msCameraZoomFactor = cameraZoomFactor1;
        r6msEnableOrthographic = true;
        r6msClipRoof = false;
        return this;
    }

    @Override
    public void setR6msClipRoof(boolean r6msClipRoof) {
        this.r6msClipRoof = r6msClipRoof;
    }

    @Override
    public void r6msDisableOrthographic() {
        r6msEnableOrthographic = false;
    }

    @ModifyVariable(method = "renderLevel", at = @At("HEAD"), argsOnly = true)
    private Matrix4f r6msModifyMatrix4fForRender(Matrix4f m) {
        if (r6msEnableOrthographic) {
            Window window = Minecraft.getInstance().getWindow();
            float width = r6msCameraZoomFactor * window.getWidth() / window.getHeight();
            float height = r6msCameraZoomFactor;
            //80 - 108
            //40 - 216
            //20 - 432
            // x * y = 8640
            Matrix4f matrix4f = new Matrix4f().ortho(-width, width, -height, height, r6msClipRoof ? 0 : -9999, 9999);
            RenderSystem.setProjectionMatrix(matrix4f);
            return matrix4f;
        } else {
            return m;
        }
    }

    @ModifyVariable(method = "prepareCullFrustum", at = @At("HEAD"), argsOnly = true)
    private Matrix4f R6msModifyMatrix4fForCull(Matrix4f m) {
        if (r6msEnableOrthographic) {
            Window window = Minecraft.getInstance().getWindow();
            float width = r6msCameraZoomFactor * window.getWidth() / window.getHeight();
            float height = r6msCameraZoomFactor;
            return new Matrix4f().ortho(-width, width, -height, height, r6msClipRoof ? 0 : -9999, 9999);
        } else {
            return m;
        }
    }
}
