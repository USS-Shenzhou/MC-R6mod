package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.client.gui.panels.MainMenuHeaderPanel;
import cn.ussshenzhou.rainbow6.client.gui.panels.MainMenuHomePanel;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import com.mojang.blaze3d.vertex.PoseStack;

/**
 * @author USS_Shenzhou
 */
public class MainMenuScreen extends AbstractR6Screen {
    private final MainMenuHeaderPanel headerPanel = new MainMenuHeaderPanel();
    private final MainMenuHomePanel homePanel = new MainMenuHomePanel();

    public static final int PADDING = 4;

    public MainMenuScreen() {
        super("Main Menu Screen");

        this.add(headerPanel);
        this.add(homePanel);
    }

    @Override
    public void layout() {
        headerPanel.setBounds(0, 0, this.width, 18);
        LayoutHelper.BBottomOfA(homePanel, 0, headerPanel, this.width, this.height - headerPanel.getHeight());
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        fill(pPoseStack, 0, 0, width, height, 0x80000000);
    }
}
