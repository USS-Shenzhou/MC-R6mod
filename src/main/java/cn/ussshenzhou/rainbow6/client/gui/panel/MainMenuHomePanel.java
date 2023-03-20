package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.screen.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.network.Test;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import cn.ussshenzhou.t88.network.PacketProxy;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class MainMenuHomePanel extends TPanel {
    private final HoverSensitiveImageButton playButton = new HoverSensitiveImageButton(
            Component.translatable("gui.r6ms.main_menu.home.play"),
            pButton -> {
                //TODO open modeSelectPanel
                //RoundPrepareScreen.newRoundPrepareScreenAndShow();
                PacketProxy.getChannel(Test.class).sendToServer(new Test());
            },
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button38_hovered.png")
    );
    /**
     * TODO This should be PlayLastPlayedModeButton. To simplify, we make it just start quick match.
     */
    private final HoverSensitiveImageButton playQuickMatchButton = new HoverSensitiveImageButton(
            Component.translatable("gui.r6ms.main_menu.home.play_quick_match"),
            pButton -> {
                startQueuing();
            },
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button38_hovered.png"));
    private final FocusSensitiveImageSelectButton queuing = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.home.queuing"),
            pButton -> {
            },
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button38_hovered.png")
    );
    private final HoverSensitiveImageButton cancelButton = new HoverSensitiveImageButton(
            Component.translatable("gui.r6ms.main_menu.home.cancel_match"),
            pButton -> {
                stopQueuing();
            },
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button38_hovered.png")) {
        @Override
        public void tickT() {
            super.tickT();
            queuing.setSelected(!this.button.isHoveredOrFocused());
        }
    };

    public MainMenuHomePanel() {
        playButton.setPadding(R6Constants.PADDING_STD);
        this.add(playButton);
        playQuickMatchButton.setPadding(R6Constants.PADDING_STD);
        playQuickMatchButton.getText().setFontSize(R6Constants.FONT_SMALL_3);
        this.add(playQuickMatchButton);
        queuing.setPadding(R6Constants.PADDING_STD);
        queuing.setSelected(true);
        queuing.setVisibleT(false);
        queuing.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(queuing);
        cancelButton.setPadding(R6Constants.PADDING_STD);
        cancelButton.setVisibleT(false);
        this.add(cancelButton);
    }

    private void startQueuing() {
        ((MainMenuScreen) this.getParentScreen()).startQueuing();
        playButton.setVisibleT(false);
        playQuickMatchButton.setVisibleT(false);
        queuing.setVisibleT(true);
        cancelButton.setVisibleT(true);
    }

    private void stopQueuing() {
        ((MainMenuScreen) this.getParentScreen()).stopQueuing();
        playButton.setVisibleT(true);
        playQuickMatchButton.setVisibleT(true);
        queuing.setVisibleT(false);
        cancelButton.setVisibleT(false);
    }

    @Override
    public void layout() {
        playButton.setBounds(10, 17, 73 + 2 * R6Constants.PADDING_STD, 30 + 2 * R6Constants.PADDING_STD);
        LayoutHelper.BRightOfA(playQuickMatchButton, -R6Constants.PADDING_STD, playButton);
        LayoutHelper.BSameAsA(queuing, playButton);
        LayoutHelper.BSameAsA(cancelButton, playQuickMatchButton);
        super.layout();
    }

}
