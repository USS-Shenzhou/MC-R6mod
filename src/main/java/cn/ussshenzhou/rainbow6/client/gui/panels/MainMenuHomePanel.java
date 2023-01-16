package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import cn.ussshenzhou.rainbow6.Rainbow6;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton;

/**
 * @author USS_Shenzhou
 */
public class MainMenuHomePanel extends TPanel {
    private final HoverSensitiveImageButton playButton = new HoverSensitiveImageButton(
            new TranslatableComponent("gui.r6ms.mainmenu.play"),
            pButton -> {

            },
            new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button38_unhovered30.png"),
            new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/button38_hovered.png")
    );

    public MainMenuHomePanel() {
        playButton.setPadding(MainMenuScreen.PADDING);
        this.add(playButton);
    }

    @Override
    public void layout() {
        playButton.setBounds(10, 17, 73 + 2 * MainMenuScreen.PADDING, 30 + 2 * MainMenuScreen.PADDING);
        super.layout();
    }
}
