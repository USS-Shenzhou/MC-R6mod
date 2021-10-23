package cn.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author USS_Shenzhou
 */
public class R6MatchMadeBarGui extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private MatrixStack matrixStack;
    float scaleFactorX = (float)(Minecraft.getInstance().getMainWindow().getScaledWidth() / 1920.0);
    float scaleFactorY = (float)(Minecraft.getInstance().getMainWindow().getScaledHeight() / 1080.0);

    public R6MatchMadeBarGui(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void setMatrixStack(MatrixStack stack) {
        this.matrixStack = stack;
    }

    public void render() {
        RenderSystem.enableBlend();
        matrixStack.push();
        //render bar
        matrixStack.scale(scaleFactorX,scaleFactorY,1);
        fill(matrixStack,0,160,1920,210,0x802195ec);
        //render text
        float scaleFactor = 0.9f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent("gui.rainbow6.matchmade"),
                (int) (400 / scaleFactor),
                (int) (172 / scaleFactor),
                0xffffff);
        matrixStack.pop();
        RenderSystem.disableBlend();

    }
}
