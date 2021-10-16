package com.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author USS_Shenzhou
 */
public class R6MapSceneGui extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private MatrixStack matrixStack;
    public final ResourceLocation BLUE_ORANGE_LOCATION = new ResourceLocation("rainbow6:textures/gui/map_scene.png");
    public final ResourceLocation AD_ICON_LOCATION = new ResourceLocation("rainbow6:textures/gui/ad_icon.png");
    float scaleFactorX = (float) (Minecraft.getInstance().getMainWindow().getScaledWidth() / 1920.0);
    float scaleFactorY = (float) (Minecraft.getInstance().getMainWindow().getScaledHeight() / 1080.0);

    public R6MapSceneGui(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void setMatrixStack(MatrixStack stack) {
        this.matrixStack = stack;
    }

    public void renderBlue() {
        render(false, ClientMatch.getIsBlueAttacker());
    }

    public void renderOrange() {
        render(true, !ClientMatch.getIsBlueAttacker());
    }

    public void render(Boolean isRotate, Boolean isAttacker) {
        RenderSystem.enableBlend();
        matrixStack.push();
        matrixStack.scale(scaleFactorX, scaleFactorY, 1);
        //render blue-orange
        float scaleFactor = 3.0f;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        minecraft.getTextureManager().bindTexture(BLUE_ORANGE_LOCATION);
        localBlit(matrixStack, (int) ((1920 - 256 * scaleFactor) / 2 / scaleFactor), (int) (340 / scaleFactor), 0, 0, 256, 32, 256, 32, isRotate);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);

        //render a/d icon
        scaleFactor = 2.5f;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        //left icon
        this.minecraft.getTextureManager().bindTexture(AD_ICON_LOCATION);
        localBlit(matrixStack, (int) (868 / scaleFactor), (int) (345 / scaleFactor), isAttacker ? 0 : 32, 0, 32, 32, 64, 32, false);
        //right icon
        localBlit(matrixStack, (int) (975 / scaleFactor), (int) (345 / scaleFactor), isAttacker ? 32 : 0, 0, 32, 32, 64, 32, false);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);

        //render team text
        //blue
        scaleFactor = 2f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawCenteredString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent("gui.rainbow6.blue"),
                isRotate ? (int) (1190 / scaleFactor) : (int) (730 / scaleFactor),
                (int) (355 / scaleFactor),
                0xffffff);
        //orange
        drawCenteredString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent("gui.rainbow6.orange"),
                isRotate ? (int) (730 / scaleFactor) : (int) (1190 / scaleFactor),
                (int) (355 / scaleFactor),
                0xffffff);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);

        //render round text
        scaleFactor = 2.75f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawCenteredString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent("gui.rainbow6.round", ClientMatch.getRound()),
                (int) (960 / scaleFactor),
                (int) (465 / scaleFactor),
                0xffffff);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);

        //render a/d text
        scaleFactor = 1.4f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawCenteredString(matrixStack, minecraft.fontRenderer,
                isAttacker ? new TranslationTextComponent("gui.rainbow6.attacker") : new TranslationTextComponent("gui.rainbow6.defender"),
                (int) (960 / scaleFactor),
                (int) (580 / scaleFactor),
                0xffffff);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);

        //render mapName
        scaleFactor = 1.4f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent(ClientMatch.getMapName()),
                (int) (120 / scaleFactor),
                (int) (80 / scaleFactor),
                0xffffff);
        matrixStack.pop();
        RenderSystem.disableBlend();

    }

    //from AbstractGui
    private static void localBlit(MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight, Boolean isRotate) {
        localBlit(matrixStack, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight, isRotate);
    }

    private static void localBlit(MatrixStack matrixStack, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, Boolean isRotate) {
        innerlocalBlit(matrixStack, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight, isRotate);
    }

    private static void innerlocalBlit(MatrixStack matrixStack, int x1, int x2, int y1, int y2, int localBlitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight, Boolean isRotate) {
        innerlocalBlit(matrixStack.getLast().getMatrix(), x1, x2, y1, y2, localBlitOffset, (uOffset + 0.0F) / (float) textureWidth, (uOffset + (float) uWidth) / (float) textureWidth, (vOffset + 0.0F) / (float) textureHeight, (vOffset + (float) vHeight) / (float) textureHeight, isRotate);
    }

    private static void innerlocalBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, int localBlitOffset, float minU, float maxU, float minV, float maxV, Boolean isRotate) {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        if (!isRotate) {
            bufferbuilder.pos(matrix, (float) x1, (float) y2, (float) localBlitOffset).tex(minU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y2, (float) localBlitOffset).tex(maxU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y1, (float) localBlitOffset).tex(maxU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x1, (float) y1, (float) localBlitOffset).tex(minU, minV).endVertex();
        } else {
            bufferbuilder.pos(matrix, (float) x1, (float) y2, (float) localBlitOffset).tex(maxU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y2, (float) localBlitOffset).tex(minU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y1, (float) localBlitOffset).tex(minU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x1, (float) y1, (float) localBlitOffset).tex(maxU, maxV).endVertex();
        }
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }
}
