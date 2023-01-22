package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.Rainbow6;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton1;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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

    public static AttentionScreen errorScreen(Component abstractMessage, Component detailMessage){
        AttentionScreen a = new AttentionScreen(abstractMessage,detailMessage);
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
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        fill(pPoseStack, 0, 0, width, height, 0x80000000);
    }

    public TLabel getAbstract() {
        return panel.abstractMessage;
    }

    public TLabel getDetail(){
        return panel.detailMessage;
    }

    public HoverSensitiveImageButton getConfirm(){
        return panel.confirmButton;
    }

    public HoverSensitiveImageButton getCancel(){
        return panel.cancelButton;
    }

    private class Panel extends TPanel {
        private final TLabel abstractMessage = new TLabel();
        private final TLabel detailMessage = new TLabel();
        private final HoverSensitiveImageButton confirmButton = new HoverSensitiveImageButton1(
                new TranslatableComponent("gui.r6ms.confirm"),
                pButton -> {
                },
                new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button_std_unhovered.png"),
                new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button_17_hovered.png")
        );
        private final HoverSensitiveImageButton cancelButton = new HoverSensitiveImageButton1(
                new TranslatableComponent("gui.r6ms.cancel"),
                pButton -> {
                },
                new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button_std_unhovered.png"),
                new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button_17_hovered.png")
        );
        private final int padding = 2;

        public Panel() {
            this.setBackground(0xff313037);
            abstractMessage.setFontSize(10);
            detailMessage.setFontSize(4);
            this.add(abstractMessage);
            this.add(detailMessage);
            confirmButton.setPadding(padding);
            cancelButton.setPadding(padding);
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
            LayoutHelper.BTopOfA(confirmButton, padding, cancelButton);
            super.layout();
        }
    }
}
