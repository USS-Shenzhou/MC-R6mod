package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class LoadingScreen extends AbstractR6Screen {
    private final TImage footer = new TImage(R6Constants.MOD_ID, "textures/gui/loading_screen_footer.png");
    private final TLabel prompt;
    private final TImage mover = new TImage(R6Constants.MOD_ID, "textures/gui/loading_screen_mover.png");

    public LoadingScreen(String promptTranslateKey) {
        super("LoadingScreen");
        this.add(mover);
        this.prompt = new TLabel(Component.translatable(promptTranslateKey));
        this.add(prompt);
        this.add(footer);
    }

    @Override
    public void layout() {
        mover.setBounds(-32, height - 12, 75, 12);
        footer.setBounds(0, height - 12, 17, 12);
        LayoutHelper.BRightOfA(prompt, 3, footer, 200, 12);
        super.layout();
    }


    @Override
    public void tick() {
        LayoutHelper.BRightOfA(mover, -75 + 16, mover);
        if (mover.getXT() > this.width) {
            mover.setBounds(0, height - 12, 75, 12);
        }
        super.tick();
    }

    @Override
    protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.fill(0, height - 12, width, height, 0x80000000);
    }

    public static class WithFullBackground extends LoadingScreen {
        private final TImage background = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/std_background_full_screen.png"));

        public WithFullBackground(String promptTranslateKey) {
            super(promptTranslateKey);
            this.add(background);
            background.setVisibleT(false);
        }

        @Override
        public void layout() {
            background.setBounds(0, 0, width, height);
            super.layout();
        }

        @Override
        protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            background.render(graphics, pMouseX, pMouseY, pPartialTick);
            super.renderBackGround(graphics, pMouseX, pMouseY, pPartialTick);
        }
    }
}
