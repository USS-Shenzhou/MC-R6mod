package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.panels.MainMenuHeaderPanel;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import cn.ussshenzhou.rainbow6.client.gui.panels.MainMenuHomePanel;

/**
 * @author USS_Shenzhou
 */
public class MainMenuScreen extends TScreen {
    private final MainMenuHeaderPanel headerPanel = new MainMenuHeaderPanel();
    private final MainMenuHomePanel homePanel = new MainMenuHomePanel();

    public static final int PADDING = 4;

    public MainMenuScreen() {
        super(new TextComponent("Main Menu"));

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
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        fill(pPoseStack, 0, 0, width, height, 0x80000000);
    }

    @Override
    public void onClose(boolean isFinal) {
        super.onClose(isFinal);
        ScreenManager.exitCurrentLayer();
    }
}
