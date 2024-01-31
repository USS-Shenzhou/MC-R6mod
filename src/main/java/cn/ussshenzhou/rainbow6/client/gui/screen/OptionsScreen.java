package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.panel.option.OptionsPanel;
import net.minecraft.client.gui.GuiGraphics;

/**
 * @author USS_Shenzhou
 */
public class OptionsScreen extends AbstractR6Screen {

    public final OptionsPanel optionsPanel = new OptionsPanel();

    public OptionsScreen() {
        super("Option Screen");
        this.add(optionsPanel);
    }

    @Override
    public void layout() {
        optionsPanel.setBounds(0, 18, this.width, this.height - 18);
        super.layout();
    }

    @Override
    protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.fill(0, 0, width, height, 0x80000000);
    }

    @Override
    public void onClose(boolean isFinal) {
        ScreenManager.mainMenuScreen.headerPanel.optionsButton.setSelected(false);
        super.onClose(isFinal);
    }
}
