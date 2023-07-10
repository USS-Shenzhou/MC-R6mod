package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.network.onlyto.server.ChooseOperatorPacket;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import cn.ussshenzhou.t88.gui.widegt.TWidget;
import cn.ussshenzhou.t88.network.NetworkHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class RoundPreOperatorsPanel extends TPanel {
    private final TImage background = new TImage(R6Constants.STD_BACKGROUND);
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
    protected void renderBackground(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        background.render(graphics, pMouseX, pMouseY, pPartialTick);
        super.renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
    }

    public void disableOperator(Operator operator) {
        if (operator == Operator.RECRUIT_A || operator == Operator.RECRUIT_D) {
            return;
        }
        //int i = Arrays.stream(Operator.getOperatorsExceptRecruit(ClientMatch.getSide())).toList().indexOf(operator);
        for (OperatorIconButton button : operators) {
            if (button.operator == operator) {
                button.disable();
            }
        }
    }

    public static class OperatorIconButton extends FocusSensitiveImageSelectButton {
        Operator operator;

        public OperatorIconButton(Operator operator) {
            super(
                    Component.literal("\n\n\n\n\n\n" + operator.getNameUpperCase()),
                    pButton -> NetworkHelper.sendToServer(new ChooseOperatorPacket(operator)),
                    operator.getIcon(52),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button40_operators.png")
            );
            this.text.setHorizontalAlignment(HorizontalAlignment.CENTER);
            this.text.setFontSize(R6Constants.FONT_TINY_2);
            this.setPadding(3);
            this.operator = operator;
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
        protected void renderText(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            this.text.render(graphics, pMouseX, pMouseY, pPartialTick);
        }

        @Override
        protected void renderChildren(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            for (TWidget tWidget : children) {
                if (tWidget.isVisibleT()) {
                    if (tWidget == backgroundImage && backgroundImageHovered.isVisibleT()) {
                        continue;
                    }
                    if (tWidget == backgroundImageHovered) {
                        continue;
                    }
                    tWidget.render(graphics, pMouseX, pMouseY, pPartialTick);
                }
            }
        }

        @Override
        public void renderTop(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            if (backgroundImageHovered.isVisibleT()) {
                renderBgImageHovered(graphics, pMouseX, pMouseY, pPartialTick);
                backgroundImage.render(graphics, pMouseX, pMouseY, pPartialTick);
                renderText(graphics, pMouseX, pMouseY, pPartialTick);
            }
            super.renderTop(graphics, pMouseX, pMouseY, pPartialTick);
        }
    }
}
