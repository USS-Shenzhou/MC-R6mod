package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import cn.ussshenzhou.t88.gui.widegt.TWidget;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class RoundPreOperatorsPanel extends TPanel {
    private final TImage background = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/std_background_full_screen.png"));
    private ArrayList<OperatorIconButton> operators = new ArrayList<>();

    public RoundPreOperatorsPanel() {
        super();
        this.add(background);
        background.setVisibleT(false);
        initOperatorIcon();
    }

    private void initOperatorIcon() {
        var recruit = new OperatorIconButton(ClientMatch.getSide() == Side.ATTACKER ? Operator.RECRUIT_A : Operator.RECRUIT_D);
        this.add(recruit);
        operators.add(recruit);
        for (Operator o : Operator.getOperatorsExceptRecruit(ClientMatch.getSide())) {
            var b = new OperatorIconButton(o);
            this.add(b);
            operators.add(b);
        }
    }

    @Override
    public void layout() {
        background.setBounds(0, 0, width, height);
        operators.get(0).setBounds(14, 57, 32, 40);
        for (int i = 1; i < operators.size(); i++) {
            operators.get(i).setBounds(
                    14 + (32 - 3) * ((i - 1) % 7),
                    57 + (32 - 3) * ((i - 1) / 7 + 1),
                    32,
                    40);
        }
        super.layout();
    }

    @Override
    protected void renderBackground(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        background.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.renderBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public static class OperatorIconButton extends FocusSensitiveImageSelectButton {

        public OperatorIconButton(Operator operator) {
            super(
                    new TextComponent("\n\n\n\n\n\n" + operator.getNameUpperCase()),
                    pButton -> {
                    },
                    operator.getIcon(52),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button40_operators.png")
            );
            this.text.setHorizontalAlignment(HorizontalAlignment.CENTER);
            this.text.setFontSize(R6Constants.FONT_TINY_2);
            this.setPadding(3);
        }

        @Override
        public void tickT() {
            super.tickT();
            this.backgroundImage.setVisibleT(true);
            this.text.setVisibleT(this.button.isHoveredOrFocused());
        }

        @Override
        public void layout() {
            backgroundImage.setBounds(padding, padding, 26, 26);
            backgroundImageHovered.setBounds(0, 0, this.getSize());
            LayoutHelper.BSameAsA(text, backgroundImage);
            LayoutHelper.BSameAsA(button, backgroundImage);
        }

        public void disable() {
            //TODO
        }

        @Override
        protected void renderText(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            this.text.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }

        @Override
        protected void renderChildren(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            for (TWidget tWidget : children) {
                if (tWidget.isVisibleT()) {
                    if (tWidget == backgroundImage && backgroundImageHovered.isVisibleT()) {
                        continue;
                    }
                    if (tWidget == backgroundImageHovered) {
                        continue;
                    }
                    tWidget.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
                }
            }
        }

        @Override
        public void renderTop(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            if (backgroundImageHovered.isVisibleT()) {
                renderBgImageHovered(pPoseStack, pMouseX, pMouseY, pPartialTick);
                backgroundImage.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
                renderText(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
            super.renderTop(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
    }
}