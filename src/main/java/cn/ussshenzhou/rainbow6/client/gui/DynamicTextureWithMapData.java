package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.t88.gui.util.Vec2i;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;

/**
 * @author USS_Shenzhou
 */
public class DynamicTextureWithMapData extends DynamicTexture {
    final float centerX;
    final float centerZ;
    final float completeWidth;
    final float completeHeight;
    final boolean turn;

    public DynamicTextureWithMapData(NativeImage pPixels, float centerX, float centerZ, float completeWidth, float completeHeight, boolean turn) {
        super(pPixels);
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.completeWidth = completeWidth;
        this.completeHeight = completeHeight;
        this.turn = turn;
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Vec2i getScreenXY(float levelX, float levelZ) {
        float guiPixelPerMeter = Minecraft.getInstance().getWindow().getGuiScaledWidth() / completeWidth;
        if (!turn) {
            float x0 = centerX - completeWidth / 2;
            float z0 = centerZ - completeHeight / 2;
            return new Vec2i((int) ((levelX - x0) * guiPixelPerMeter), (int) ((levelZ - z0) * guiPixelPerMeter));
        } else {
            //TODO need test
            float x0 = centerX + completeHeight / 2;
            float z0 = centerZ - completeWidth / 2;
            return new Vec2i((int) ((x0 - levelX) * guiPixelPerMeter), (int) ((levelZ - z0) * guiPixelPerMeter));
        }
    }
}
