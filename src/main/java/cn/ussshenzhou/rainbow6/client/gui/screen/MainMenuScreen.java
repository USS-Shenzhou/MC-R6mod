package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.panel.MainMenuHeaderPanel;
import cn.ussshenzhou.rainbow6.client.gui.panel.MainMenuHomePanel;
import cn.ussshenzhou.rainbow6.client.gui.panel.MainMenuOperatorsPanel;
import cn.ussshenzhou.rainbow6.client.gui.panel.MainMenuOptionsPanel;
import cn.ussshenzhou.rainbow6.client.gui.widget.QueuingForMatchBar;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
public class MainMenuScreen extends AbstractR6Screen {
    private final MainMenuHeaderPanel headerPanel = new MainMenuHeaderPanel();
    public final QueuingForMatchBar queuingForMatchBar = new QueuingForMatchBar();

    public final MainMenuHomePanel homePanel = new MainMenuHomePanel();
    public final MainMenuOperatorsPanel operatorsPanel = new MainMenuOperatorsPanel();
    public final MainMenuOptionsPanel optionsPanel = new MainMenuOptionsPanel();

    /**
     * Only use for UI change (headerPanel move).
     */
    private boolean queuing = false;

    public MainMenuScreen() {
        super("Main Menu Screen");
        this.add(headerPanel);
        queuingForMatchBar.setVisibleT(false);
        this.add(queuingForMatchBar);

        this.add(homePanel);
        headerPanel.getHomeButton().setSelected(true);
        operatorsPanel.setVisibleT(false);
        this.add(operatorsPanel);
        this.add(optionsPanel);
        optionsPanel.setVisibleT(false);
    }

    public void setVisiblePanel(TPanel visiblePanel) {
        homePanel.setVisibleT(false);
        operatorsPanel.setVisibleT(false);
        optionsPanel.setVisibleT(false);
        visiblePanel.setVisibleT(true);
    }

    @Override
    public void layout() {
        queuingForMatchBar.setBounds(0, 0, this.width, 12);
        headerPanel.setBounds(0, queuing ? 12 : 0, this.width, 18);
        //Don't use LayoutHelper, cause headerPanel may move.
        homePanel.setBounds(0, headerPanel.getHeight(), this.width, this.height - headerPanel.getHeight());
        LayoutHelper.BSameAsA(operatorsPanel, homePanel);
        LayoutHelper.BSameAsA(optionsPanel, operatorsPanel);
        super.layout();
    }

    /**
     * TODO Only provide quick match for now. No more parameters needed.
     */
    public void startQueuing() {
        LayoutHelper.BBottomOfA(headerPanel, 0, queuingForMatchBar, headerPanel.getSize());
        headerPanel.layout();
        queuingForMatchBar.start();
        queuing = true;
    }

    public void stopQueuing() {
        headerPanel.setBounds(0, 0, this.width, 18);
        headerPanel.layout();
        queuingForMatchBar.stop();
        queuing = false;
    }

    public boolean isQueuing() {
        return queuing;
    }

    @Override
    protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.fill(0, 0, width, height, 0x80000000);
    }

    @Override
    public void onClose(boolean isFinal) {
        super.onClose(isFinal);
        if (queuing) {
            ScreenManager.mainMenuScreenBuffer = this;
            HudManager.add(queuingForMatchBar);
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE && optionsPanel.isVisibleT()) {
            setVisiblePanel(homePanel);
            headerPanel.getHomeButton().setSelected(true);
            headerPanel.getOperatorsButton().setSelected(false);
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}
