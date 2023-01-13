package ussshenzhou.rainbow6.client.gui.widgets;

import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TButton;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class FocusSensitiveImageButton extends TPanel {
    private TImage backgroundImage;
    private TImage backgroundImageFocused;
    private TButton button;
    private TLabel text;

    //TODO paddingX/Y
    private int padding = 0;

    public FocusSensitiveImageButton(Component text, Button.OnPress onPress, ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationFocused) {
        super();
        this.text = new TLabel(text);
        this.button = new TButton(new TextComponent(""), onPress) {
            @Override
            public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
                return;
            }
        };
        this.backgroundImage = new TImage(backgroundImageLocation);
        this.backgroundImageFocused = new TransitionImage(backgroundImageLocationFocused);
        this.backgroundImageFocused.setVisibleT(false);

        this.add(this.backgroundImage);
        this.add(this.backgroundImageFocused);
        this.add(this.button);
        this.add(this.text);
    }

    @Override
    public void layout() {
        backgroundImage.setBounds(padding, padding, this.width - padding * 2, this.height - padding * 2);
        backgroundImageFocused.setBounds(0, 0, this.getSize());
        LayoutHelper.BSameAsA(text, backgroundImage);
        button.setBounds(padding / 2, padding / 2, this.width - padding, this.height - padding);

        super.layout();
    }

    @Override
    public void tickT() {
        if (button.isHoveredOrFocused()) {
            backgroundImage.setVisibleT(false);
            backgroundImageFocused.setVisibleT(true);
        } else {
            backgroundImage.setVisibleT(true);
            backgroundImageFocused.setVisibleT(false);
        }
        super.tickT();
    }

    @Override
    protected void renderChildren(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderChildren(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public TImage getBackgroundImage() {
        return backgroundImage;
    }

    public TImage getBackgroundImageFocused() {
        return backgroundImageFocused;
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

    public class TransitionImage extends cn.ussshenzhou.t88.gui.widegt.TImage {

        public TransitionImage(ResourceLocation imageLocation) {
            super(imageLocation);
        }

        private boolean inTransition = false;
        private boolean transited = false;
        private int transitionTimeMinus1 = 2;
        private float transitionTick = 0;

        @Override
        public void tickT() {
            if (isVisibleT() && !transited) {
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
            if (!isVisibleT()) {
                inTransition = false;
                transited = false;
                transitionTick = 0;
            }
            super.tickT();
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            if (inTransition && transitionTick < transitionTimeMinus1) {
                //backgroundImageFocused will be stretched to backgroundImage, then enlarged.
                pPoseStack.pushPose();
                float minScaleX = (float) backgroundImage.getWidth() / backgroundImageFocused.getWidth();
                float minScaleY = (float) backgroundImage.getHeight() / backgroundImageFocused.getHeight();
                float scaleX = minScaleX + transitionTick / transitionTimeMinus1 * (1 - minScaleX);
                float scaleY = minScaleY + transitionTick / transitionTimeMinus1 * (1 - minScaleY);
                float compensationRelativeX = (1 - scaleX) / (1 - minScaleX) * padding;
                float compensationRelativeY = (1 - scaleY) / (1 - minScaleY) * padding;
                //scale compensation = absolute + relative
                pPoseStack.translate(
                        (1 - scaleX) * this.getX() + compensationRelativeX,
                        (1 - scaleY) * this.getY() + compensationRelativeY,
                        0);
                pPoseStack.scale(scaleX, scaleY, 1);
                super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
                pPoseStack.popPose();
                transitionTick += pPartialTick;
            } else {
                super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        }

        public int getTransitionTimeMinus1() {
            return transitionTimeMinus1;
        }

        public void setTransitionTimeMinus1(int transitionTimeMinus1) {
            this.transitionTimeMinus1 = transitionTimeMinus1;
        }
    }
}
