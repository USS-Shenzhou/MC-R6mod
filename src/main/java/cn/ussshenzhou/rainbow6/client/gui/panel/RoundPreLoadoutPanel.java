package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.gui.GuiGraphics;

/**
 * @author USS_Shenzhou
 */
public class RoundPreLoadoutPanel extends TPanel {
    private final TImage background = new TImage(R6Constants.STD_BACKGROUND);

    public RoundPreLoadoutPanel() {
        super();
        this.add(background);
        background.setVisibleT(false);
    }

    @Override
    public void layout() {
        background.setBounds(0, 0, width, height);
        super.layout();
    }

    @Override
    protected void renderBackground(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        background.render(graphics, pMouseX, pMouseY, pPartialTick);
        super.renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
    }
}
