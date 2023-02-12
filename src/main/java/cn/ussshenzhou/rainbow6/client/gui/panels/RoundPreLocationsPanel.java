package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
public abstract class RoundPreLocationsPanel extends TPanel {
    private static final TImage backgroundMaskL = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_left.png"));
    private static final TImage backgroundMaskR = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_right.png"));

    public RoundPreLocationsPanel() {
        super();
        backgroundMaskL.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskL);
        backgroundMaskR.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskR);
    }

    @Override
    public void layout() {
        backgroundMaskL.setBounds(0, 0, 50, height);
        backgroundMaskR.setBounds(width - 50, 0, 50, height);
        super.layout();
    }

    public abstract boolean isReady();
}
