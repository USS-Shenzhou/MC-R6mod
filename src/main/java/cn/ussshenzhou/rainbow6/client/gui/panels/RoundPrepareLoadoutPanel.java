package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class RoundPrepareLoadoutPanel extends TPanel {
    private final TImage background = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/std_background_full_screen.png"));

    public RoundPrepareLoadoutPanel() {
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
    protected void renderBackground(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        background.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.renderBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
