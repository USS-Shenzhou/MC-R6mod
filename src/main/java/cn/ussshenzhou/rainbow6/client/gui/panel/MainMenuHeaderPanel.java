package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.screen.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class MainMenuHeaderPanel extends TPanel {
    private final FocusSensitiveImageSelectButton homeButton = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.home"),
            pButton -> {
                MainMenuScreen mainMenu = getMainMenuScreen();
                mainMenu.setVisiblePanel(mainMenu.homePanel);
                getOperatorsButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );
    private final FocusSensitiveImageSelectButton operatorsButton = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.operators"),
            pButton -> {
                MainMenuScreen mainMenu = getMainMenuScreen();
                mainMenu.setVisiblePanel(mainMenu.operatorsPanel);
                getHomeButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );

    private final HoverSensitiveImageButton optionsButton = new HoverSensitiveImageButton(
            Component.empty(),
            pButton -> {
                MainMenuScreen mainMenu = getMainMenuScreen();
                mainMenu.setVisiblePanel(mainMenu.optionsPanel);
                getHomeButton().setSelected(false);
                getOperatorsButton().setSelected(false);
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

    private MainMenuScreen getMainMenuScreen(){
        return ((MainMenuScreen) getParentScreen());
    }

    @Override
    public void layout() {
        homeButton.setBounds(25, 0, 58, height);
        LayoutHelper.BRightOfA(operatorsButton, 0, homeButton);
        optionsButton.setBounds(width - 18, 0, 18, 18);
        super.layout();
    }

    public FocusSensitiveImageSelectButton getHomeButton() {
        return homeButton;
    }

    public FocusSensitiveImageSelectButton getOperatorsButton() {
        return operatorsButton;
    }
}
