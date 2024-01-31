package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.screen.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.screen.OptionsScreen;
import cn.ussshenzhou.t88.gui.advanced.TFocusSensitiveImageSelectButton;
import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.common.NeoForge;

/**
 * @author USS_Shenzhou
 */
public class MainMenuHeaderPanel extends TPanel {
    public final TFocusSensitiveImageSelectButton homeButton = new TFocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.home"),
            pButton -> {
                MainMenuScreen mainMenu = getMainMenuScreen();
                mainMenu.setVisiblePanel(mainMenu.homePanel);
                getOperatorsButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );
    public final TFocusSensitiveImageSelectButton operatorsButton = new TFocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.operators"),
            pButton -> {
                MainMenuScreen mainMenu = getMainMenuScreen();
                mainMenu.setVisiblePanel(mainMenu.operatorsPanel);
                getHomeButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );

    public final TFocusSensitiveImageSelectButton optionsButton = new TFocusSensitiveImageSelectButton(
            Component.empty(),
            pButton -> {
                getHomeButton().setSelected(false);
                getOperatorsButton().setSelected(false);
                ScreenManager.showNewLayerOverBg(new OptionsScreen());
            },
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_options.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_options_hovered.png")
    );

    public MainMenuHeaderPanel() {
        this.setBackground(0x80000000);

        homeButton.setPadding(R6Constants.PADDING_SMALL);
        homeButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        operatorsButton.setPadding(R6Constants.PADDING_SMALL);
        operatorsButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(homeButton);
        this.add(operatorsButton);
        this.add(optionsButton);
        optionsButton.setBackground(0x33ffffff);
    }

    private MainMenuScreen getMainMenuScreen() {
        return ((MainMenuScreen) getParentScreen());
    }

    @Override
    public void layout() {
        homeButton.setBounds(25, 0, 58, height);
        LayoutHelper.BRightOfA(operatorsButton, 0, homeButton);
        optionsButton.setBounds(width - 18, 0, 18, 18);
        super.layout();
    }

    public TFocusSensitiveImageSelectButton getHomeButton() {
        return homeButton;
    }

    public TFocusSensitiveImageSelectButton getOperatorsButton() {
        return operatorsButton;
    }
}
