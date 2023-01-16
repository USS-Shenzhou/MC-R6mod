package cn.ussshenzhou.rainbow6.client.gui.widgets;

import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class HoverSensitiveImageButton extends TPanel {
    private TImage backgroundImage;
    private TImage backgroundImageHovered;
    private TButton button;
    private TLabel text;

    private int padding = 0;
    private boolean inTransition = false;
    private boolean transited = false;
    private int transitionTimeMinus1 = 2;
    private float transitionTick = 0;

    public HoverSensitiveImageButton(Component text1, Button.OnPress onPress, ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationFocused) {
        super();
        this.text = new TLabel(text1);
        this.button = new TButton(new TextComponent(""), onPress) {
            @Override
            public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
                return;
            }
        };
        this.backgroundImage = new TImage(backgroundImageLocation);
        this.backgroundImageHovered = new TImage(backgroundImageLocationFocused);
        this.backgroundImageHovered.setVisibleT(false);

        this.add(this.backgroundImage);
        this.add(this.backgroundImageHovered);
        this.add(this.button);
        this.add(this.text);
    }

    @Override
    public void layout() {
        backgroundImage.setBounds(padding, padding, this.width - padding * 2, this.height - padding * 2);
        backgroundImageHovered.setBounds(0, 0, this.getSize());
        LayoutHelper.BSameAsA(text, backgroundImage);
        button.setBounds(padding / 2, padding / 2, this.width - padding, this.height - padding);

        super.layout();
    }

    @Override
    public void tickT() {
        if (button.isHoveredOrFocused()) {
            backgroundImage.setVisibleT(false);
            backgroundImageHovered.setVisibleT(true);
        } else {
            backgroundImage.setVisibleT(true);
            backgroundImageHovered.setVisibleT(false);
        }

        if (backgroundImageHovered.isVisibleT() && !transited) {
            if (!inTransition) {
                //begin transit
                inTransition = true;
            }
            if (transitionTick >= transitionTimeMinus1) {
                //complete transit
                inTransition = false;
                transited = true;
                transitionTick = 0;
            }
        }
        if (!backgroundImageHovered.isVisibleT()) {
            inTransition = false;
            transited = false;
            transitionTick = 0;
        }

        super.tickT();
    }

    @Override
    protected void renderChildren(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        for (TWidget tWidget : children) {
            if (tWidget.isVisibleT()) {
                if (tWidget == text && backgroundImageHovered.isVisibleT()) {
                    renderText(pPoseStack, pMouseX, pMouseY, pPartialTick);
                    continue;
                }
                if (tWidget == backgroundImageHovered) {
                    renderBgImageHovered(pPoseStack, pMouseX, pMouseY, pPartialTick);
                    continue;
                }
                tWidget.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        }
    }

    /**
     * backgroundImage will be stretched to the size of backgroundImage, then magnified to original.
     */
    private void renderBgImageHovered(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (padding != 0 && inTransition && transitionTick < transitionTimeMinus1) {
            float minScaleX = (float) backgroundImage.getWidth() / backgroundImageHovered.getWidth();
            float minScaleY = (float) backgroundImage.getHeight() / backgroundImageHovered.getHeight();
            float scaleX = minScaleX + transitionTick / transitionTimeMinus1 * (1 - minScaleX);
            float scaleY = minScaleY + transitionTick / transitionTimeMinus1 * (1 - minScaleY);
            float compensationRelativeX = (1 - scaleX) / (1 - minScaleX) * padding;
            float compensationRelativeY = (1 - scaleY) / (1 - minScaleY) * padding;
            pPoseStack.pushPose();
            //scale compensation = absolute + relative
            pPoseStack.translate(
                    (1 - scaleX) * backgroundImageHovered.getX() + compensationRelativeX,
                    (1 - scaleY) * backgroundImageHovered.getY() + compensationRelativeY,
                    0);
            pPoseStack.scale(scaleX, scaleY, 1);
            backgroundImageHovered.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            pPoseStack.popPose();
            transitionTick += pPartialTick;
        } else {
            backgroundImageHovered.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
    }

    /**
     * Text will be magnified when focused. To keep text's original shape, scaling will not calculate separately.
     */
    private void renderText(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        float maxScaleX = (float) backgroundImageHovered.getWidth() / backgroundImage.getWidth();
        float maxScaleY = (float) backgroundImageHovered.getHeight() / backgroundImage.getHeight();
        float maxScale = Math.min(maxScaleX, maxScaleY);
        if (padding != 0 && inTransition && transitionTick < transitionTimeMinus1) {
            //float scaleX = 1 + transitionTick / transitionTimeMinus1 * (maxScaleX - 1);
            //float scaleY = 1 + transitionTick / transitionTimeMinus1 * (maxScaleY - 1);
            float scale = 1 + transitionTick / transitionTimeMinus1 * (maxScale - 1);
            renderTextInternal(pPoseStack, pMouseX, pMouseY, pPartialTick, scale, maxScale);
        } else {
            renderTextInternal(pPoseStack, pMouseX, pMouseY, pPartialTick, maxScale, maxScale);
        }
    }

    private void renderTextInternal(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick, float scale, float maxScale) {
        //float compensationRelativeX = (1 - scaleX) / (maxScaleX - 1) * padding;
        //float compensationRelativeY = (1 - scaleY) / (maxScaleY - 1) * padding;
        float compensationRelative = (1 - scale) / (maxScale - 1) * padding;
        pPoseStack.pushPose();
        pPoseStack.translate(
                (1 - scale) * text.getX() + compensationRelative,
                (1 - scale) * text.getY() + compensationRelative,
                0);
        pPoseStack.scale(scale, scale, 1);
        text.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        pPoseStack.popPose();
    }

    public TImage getBackgroundImage() {
        return backgroundImage;
    }

    public TImage getBackgroundImageHovered() {
        return backgroundImageHovered;
    }

    public TButton getButton() {
        return button;
    }

    public TLabel getText() {
        return text;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getTransitionTimeMinus1() {
        return transitionTimeMinus1;
    }

    public void setTransitionTimeMinus1(int transitionTimeMinus1) {
        this.transitionTimeMinus1 = transitionTimeMinus1;
    }
}
