package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.GuiUtil;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class AttentionScreen extends AbstractR6Screen {
    private final Panel panel = new Panel();
    private final boolean canCancel;

    public AttentionScreen(Component abstractMessage, Component detailMessage) {
        this(abstractMessage, detailMessage, false);
    }

    public AttentionScreen(Component abstractMessage, Component detailMessage, boolean canCancel) {
        super("Attention Screen");
        this.canCancel = canCancel;
        this.add(panel);
        if (!canCancel) {
            panel.cancelButton.setVisibleT(false);
        }
        panel.abstractMessage.setText(abstractMessage);
        panel.detailMessage.setText(detailMessage);
    }

    public static AttentionScreen errorScreen(Component abstractMessage, Component detailMessage) {
        AttentionScreen a = new AttentionScreen(abstractMessage, detailMessage);
        a.panel.confirmButton.getButton().setOnPress(pButton -> {
            a.onClose(true);
        });
        return a;
    }

    @Override
    public void layout() {
        panel.setBounds((width - 157) / 2, 0, 157, height);
        super.layout();
    }

    @Override
    protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.fill(0, 0, width, height, 0x80000000);
    }

    public TLabel getAbstract() {
        return panel.abstractMessage;
    }

    public TLabel getDetail() {
        return panel.detailMessage;
    }

    public THoverSensitiveImageButton getConfirm() {
        return panel.confirmButton;
    }

    public THoverSensitiveImageButton getCancel() {
        return panel.cancelButton;
    }

    private class Panel extends TPanel {
        private final TLabel abstractMessage = new TLabel();
        private final TLabel detailMessage = new TLabel();
        private final THoverSensitiveImageButton confirmButton = new THoverSensitiveImageButton(
                Component.translatable("gui.r6ms.confirm"),
                pButton -> {
                },
                GuiUtil.buttonStdUnhovered(),
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_17_hovered.png")
        );
        private final THoverSensitiveImageButton cancelButton = new THoverSensitiveImageButton(
                Component.translatable("gui.r6ms.cancel"),
                pButton -> {
                },
                GuiUtil.buttonStdUnhovered(),
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_17_hovered.png")
        );

        public Panel() {
            this.setBackground(0xff313037);
            abstractMessage.setFontSize(10);
            detailMessage.setFontSize(4);
            this.add(abstractMessage);
            this.add(detailMessage);
            confirmButton.setPadding(R6Constants.PADDING_SMALL);
            cancelButton.setPadding(R6Constants.PADDING_SMALL);
            this.add(confirmButton);
            this.add(cancelButton);
        }

        @Override
        public void layout() {
            int gap = 9;
            abstractMessage.setBounds(gap, 40, width - 2 * gap, abstractMessage.getPreferredSize().y);
            LayoutHelper.BBottomOfA(detailMessage, 8, abstractMessage, abstractMessage.getWidth(), detailMessage.getPreferredSize().y);
            int gap1 = 5;
            cancelButton.setBounds(gap1, height - 20 - 17, 145, 17);
            LayoutHelper.BTopOfA(confirmButton, R6Constants.PADDING_SMALL, cancelButton);
            super.layout();
        }
    }
}
