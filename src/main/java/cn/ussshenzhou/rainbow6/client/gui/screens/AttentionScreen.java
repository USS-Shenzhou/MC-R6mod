package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.Rainbow6;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton1;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class AttentionScreen extends AbstractR6Screen {
    private final Panel backGroundPanel = new Panel();
    private final boolean canCancel;

    public AttentionScreen() {
        this(false);
    }

    public AttentionScreen(boolean canCancel) {
        super("Attention Screen");
        this.canCancel = canCancel;
        this.add(backGroundPanel);
        if (!canCancel) {
            backGroundPanel.cancelButton.setVisibleT(false);
        }
    }

    @Override
    public void layout() {
        backGroundPanel.setBounds((width - 157) / 2, 0, 157, height);
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        fill(pPoseStack, 0, 0, width, height, 0x80000000);
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
            LayoutHelper.BBottomOfA(detailMessage, 10, abstractMessage, abstractMessage.getWidth(), height / 2);
            int gap1 = 5;
            cancelButton.setBounds(gap1, height - 20 - 17, 145, 17);
            LayoutHelper.BTopOfA(confirmButton, padding, cancelButton);
            super.layout();
        }
    }
}
