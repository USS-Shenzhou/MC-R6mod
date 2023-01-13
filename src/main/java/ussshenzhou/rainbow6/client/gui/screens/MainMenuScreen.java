package ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.t88.gui.screen.TScreen;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import ussshenzhou.rainbow6.Rainbow6;
import ussshenzhou.rainbow6.client.gui.ScreenManager;
import ussshenzhou.rainbow6.client.gui.widgets.FocusSensitiveImageButton;

/**
 * @author USS_Shenzhou
 */
public class MainMenuScreen extends TScreen {
    private final TPanel headerBackGround = new TPanel();
    private final FocusSensitiveImageButton playButton = new FocusSensitiveImageButton(
            //new TranslatableComponent("gui.r6ms.mainmenu.play"),
            new TextComponent(""),
            pButton -> {
            },
            new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/test2.png"),
            new ResourceLocation(Rainbow6.MOD_ID, "textures/gui/test1.png")
    );

    private static final int PADDING = 3;

    public MainMenuScreen() {
        super(new TextComponent("Main Menu"));

        headerBackGround.setBackground(0x80000000);

        playButton.setPadding(PADDING);
        playButton.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        playButton.getBackgroundImageFocused().setImageFit(ImageFit.STRETCH);

        this.add(headerBackGround);
        this.add(playButton);
    }

    @Override
    public void layout() {
        headerBackGround.setBounds(0, 0, this.width, 20);
        playButton.setBounds(15, 40, 75 + 2 * PADDING, 30 + 2 * PADDING);

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
