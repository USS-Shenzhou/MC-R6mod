package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.widgets.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class MainMenuHeaderPanel extends TPanel {
    private final FocusSensitiveImageSelectButton homeButton = new FocusSensitiveImageSelectButton(
            new TranslatableComponent("gui.r6ms.main-menu.home"),
            pButton -> {
                MainMenuScreen mainMenu = ((MainMenuScreen) getParentScreen());
                mainMenu.setVisiblePanel(mainMenu.homePanel);
                getOperatorsButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );
    private final FocusSensitiveImageSelectButton operatorsButton = new FocusSensitiveImageSelectButton(
            new TranslatableComponent("gui.r6ms.main-menu.operators"),
            pButton -> {
                MainMenuScreen mainMenu = ((MainMenuScreen) getParentScreen());
                mainMenu.setVisiblePanel(mainMenu.operatorsPanel);
                getHomeButton().setSelected(false);
            },
            R6Constants.PLACEHOLDER_IMAGE,
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected.png")
    );

    public MainMenuHeaderPanel() {
        this.setBackground(0x80000000);

        homeButton.setPadding(R6Constants.PADDING_SMALL);
        homeButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        operatorsButton.setPadding(R6Constants.PADDING_SMALL);
        operatorsButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(homeButton);
        this.add(operatorsButton);
    }

    @Override
    public void layout() {
        homeButton.setBounds(25, 0, 58, height);
        LayoutHelper.BRightOfA(operatorsButton, 0, homeButton);
        super.layout();
    }

    public FocusSensitiveImageSelectButton getHomeButton() {
        return homeButton;
    }

    public FocusSensitiveImageSelectButton getOperatorsButton() {
        return operatorsButton;
    }
}
