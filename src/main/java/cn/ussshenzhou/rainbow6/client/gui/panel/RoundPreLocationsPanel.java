package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
public abstract class RoundPreLocationsPanel extends TPanel {
    private static final TImage backgroundMaskL = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_left.png"));
    private static final TImage backgroundMaskR = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_right.png"));
    private final TLabel prompt;

    public RoundPreLocationsPanel(TLabel prompt) {
        super();
        backgroundMaskL.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskL);
        backgroundMaskR.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskR);
        this.prompt = prompt;
        prompt.setFontSize(R6Constants.FONT_TINY_2);
        this.add(prompt);
    }

    @Override
    public void layout() {
        backgroundMaskL.setBounds(0, 0, 50, height);
        backgroundMaskR.setBounds(width - 50, 0, 50, height);
        int i = prompt.getPreferredSize().y;
        prompt.setBounds(12 + 4, 65 - i, 119, i);
        super.layout();
    }

    public abstract boolean isReady();
}
