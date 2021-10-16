package com.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

/**
 * @author USS_Shenzhou
 */
public class R6PlayerIconListBackGroundGui extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    public final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:textures/gui/playericonlist.png");
    public final ResourceLocation AD_ICON_LOCATION = new ResourceLocation("rainbow6:textures/gui/ad_icon.png");
    public final ResourceLocation OPERATORS_LOCATION = new ResourceLocation("rainbow6:textures/gui/operators.png");
    private MatrixStack matrixStack;

    public R6PlayerIconListBackGroundGui(MatrixStack matrixStack) {
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

    private void render(Boolean isOrange, Boolean isAttacker) {
        RenderSystem.enableBlend();
        matrixStack.push();
        matrixStack.scale(0.5f, 0.5f, 1);
        //blue-orange background
        this.minecraft.getTextureManager().bindTexture(TEXTURE_LOCATION);
        localBlit(matrixStack, (int) (width - 288), (int) (height * 0.1), 0, 0, 576, 32, 576, 32, isOrange);
        //left a/d icon
        this.minecraft.getTextureManager().bindTexture(AD_ICON_LOCATION);
        localBlit(matrixStack, (int) (width - 110), (int) (height * 0.1), isAttacker ? 0 : 32, 0, 32, 32, 64, 32, false);
        //right a/d icon
        localBlit(matrixStack, (int) (width + 79), (int) (height * 0.1), isAttacker ? 32 : 0, 0, 32, 32, 64, 32, false);
        //matrixStack.scale(2f, 2f, 1);
        //left players icon
        minecraft.getTextureManager().bindTexture(OPERATORS_LOCATION);
        int i = width - 150;
        if (isOrange) {
            //orange at left
            for (String s : ClientMatch.getTeamOrangePLayers().values()) {
                blit(matrixStack, i, (int) (height * 0.1),
                        InGameClientProperties.getOperatorLogo(s).getX(),
                        InGameClientProperties.getOperatorLogo(s).getY(),
                        32, 32,
                        128, 576
                );
                i = i - 34;
            }
            //blue at right
            i = width + 118;
            for (NetworkPlayerInfo n : ClientMatch.getTeamBluePlayers().keySet()) {
                if (ClientMatch.getIsSpotted(n)) {
                    String operator = ClientMatch.getTeamBluePlayers().get(n);
                    blit(matrixStack, i, (int) (height * 0.1),
                            InGameClientProperties.getOperatorLogo(operator).getX(),
                            InGameClientProperties.getOperatorLogo(operator).getY(),
                            32, 32,
                            128, 576
                    );
                } else {
                    blit(matrixStack, i, (int) (height * 0.1),
                            InGameClientProperties.getOperatorLogo("unknown").getX(),
                            InGameClientProperties.getOperatorLogo("unknown").getY(),
                            32, 32,
                            128, 576
                    );
                }
                i = i + 34;
            }
        } else {
            //blue at left
            for (String s : ClientMatch.getTeamBluePlayers().values()) {
                blit(matrixStack, i, (int) (height * 0.1),
                        InGameClientProperties.getOperatorLogo(s).getX(),
                        InGameClientProperties.getOperatorLogo(s).getY(),
                        32, 32,
                        128, 576
                );
                i = i - 34;
            }
            //orange at right
            i = width + 118;
            for (NetworkPlayerInfo n : ClientMatch.getTeamOrangePLayers().keySet()) {
                if (ClientMatch.getIsSpotted(n)) {
                    String operator = ClientMatch.getTeamOrangePLayers().get(n);
                    blit(matrixStack, i, (int) (height * 0.1),
                            InGameClientProperties.getOperatorLogo(operator).getX(),
                            InGameClientProperties.getOperatorLogo(operator).getY(),
                            32, 32,
                            128, 576
                    );
                } else {
                    blit(matrixStack, i, (int) (height * 0.1),
                            InGameClientProperties.getOperatorLogo("unknown").getX(),
                            InGameClientProperties.getOperatorLogo("unknown").getY(),
                            32, 32,
                            128, 576
                    );
                }
                i = i + 34;
            }
        }
        //team blue score
        float scaleFactor = 2.5f;
        matrixStack.scale(scaleFactor, scaleFactor, scaleFactor);
        drawString(matrixStack, minecraft.fontRenderer,
                ClientMatch.getBlueScoreAsString(),
                isOrange ? (int) ((width + 59.5) / scaleFactor) : (int) ((width - 68) / scaleFactor),
                (int) (height * 0.13 / scaleFactor),
                0x2195ec);
        //team orange score
        drawString(matrixStack, minecraft.fontRenderer,
                ClientMatch.getOrangeScoreAsString(),
                isOrange ? (int) ((width - 68) / scaleFactor) : (int) ((width + 59.5) / scaleFactor),
                (int) (height * 0.13 / scaleFactor),
                0xeb7319);

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
        } else //if (isRotate && !isFlip) {
        {
            bufferbuilder.pos(matrix, (float) x1, (float) y2, (float) localBlitOffset).tex(maxU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y2, (float) localBlitOffset).tex(minU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y1, (float) localBlitOffset).tex(minU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x1, (float) y1, (float) localBlitOffset).tex(maxU, maxV).endVertex();
        } /*else {
            bufferbuilder.pos(matrix, (float) x1, (float) y2, (float) localBlitOffset).tex(maxU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y2, (float) localBlitOffset).tex(minU, maxV).endVertex();
            bufferbuilder.pos(matrix, (float) x2, (float) y1, (float) localBlitOffset).tex(minU, minV).endVertex();
            bufferbuilder.pos(matrix, (float) x1, (float) y1, (float) localBlitOffset).tex(maxU, minV).endVertex();
        }*/
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }
}
