package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.GuiUtil;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class RoundPreLoadoutPanel extends TPanel {
    private final TImage background = new TImage(R6Constants.STD_BACKGROUND);
    private final THoverSensitiveImageButton ready = new THoverSensitiveImageButton(Component.translatable("gui.r6ms.round_prepare.loadout.ready"),
            pButton -> {
                //TODO
            },
            GuiUtil.buttonStdUnhovered(),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button23_hovered.png"));

    public RoundPreLoadoutPanel() {
        super();
        this.addAll(background, ready);
        background.setVisibleT(false);
        GuiUtil.defaultInit(ready);
    }

    @Override
    public void layout() {
        background.setBounds(0, 0, width, height);
        ready.setBounds(14, 57, GuiUtil.button23PreferredSize());
        super.layout();
    }

    @Override
    protected void renderBackground(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        background.render(graphics, pMouseX, pMouseY, pPartialTick);
        super.renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
    }
}
