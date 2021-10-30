package cn.ussshenzhou.rainbow6.gui;

import cn.ussshenzhou.rainbow6.items.EE1DController;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;

/**
 * @author USS_Shenzhou
 */
public class EE1DWarnGui extends AbstractGui {
    public static boolean isRender = false;
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private MatrixStack matrixStack;
    private static int tickExisted = 0;
    public final ResourceLocation resourceLocation = new ResourceLocation("rainbow6:textures/gui/lion_logo.png");

    public EE1DWarnGui(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void render() {
        RenderSystem.enableBlend();
        matrixStack.push();
        float scaleFactor = 0.6f;
        switch (tickExisted) {
            case 28:
            case 31:
                scaleFactor = 0.7f;
                break;
            case 29:
            case 30:
                scaleFactor = 0.8f;
                break;
            default:
                break;
            }
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        if (tickExisted < EE1DController.PREPARE_TIME) {
            renderAccordingTick(matrixStack, scaleFactor);
        } else {
            renderBackAccordingTick(matrixStack, scaleFactor);
        }
        matrixStack.pop();
        RenderSystem.disableBlend();
    }

    private void renderAccordingTick(MatrixStack matrixStack, float scaleFactor) {
        float i = EE1DController.PREPARE_TIME / 15f;
        int stage = (int) (tickExisted / i);
        int u = (int) (stage % 5f) * 32;
        int v = (int) (stage / 5f) * 32;
        this.minecraft.getTextureManager().bindTexture(resourceLocation);
        try {
            //LogManager.getLogger().info(tickExisted + "------A " + u / 32 + "   " + v / 32 + "   " + (u / 32 + v / 32 * 5));
            blit(matrixStack, (int) (width / scaleFactor / 2 - 16), (int) (width / scaleFactor * 0.1 - 16), u, v, 32, 32, 160, 96);
        } catch (Exception ignored) {
        }
    }

    private void renderBackAccordingTick(MatrixStack matrixStack, float scaleFactor) {
        float i = EE1DController.SCAN_TIME / 15f;
        int stage = (int) ((tickExisted - EE1DController.PREPARE_TIME) / i);
        int u = (int) ((14 - stage) % 5f) * 32;
        int v = (int) ((14 - stage) / 5f) * 32;
        this.minecraft.getTextureManager().bindTexture(resourceLocation);
        try {
            if (!ClientMatch.getIsAttacker()){
                RenderSystem.color4f(0.9843f,8117647f,0.062745f,1.0f);
            }
            blit(matrixStack, (int) (width / scaleFactor / 2 - 16), (int) (width / scaleFactor * 0.1 - 16), u, v, 32, 32, 160, 96);
            RenderSystem.color4f(1.0f,1.0f,1.0f,1.0f);
        } catch (Exception ignored) {
        }
    }

    public static void tick() {
        tickExisted++;
        if (tickExisted >= EE1DController.PREPARE_TIME + EE1DController.SCAN_TIME) {
            tickExisted = 0;
            isRender = false;
        }
    }
}
