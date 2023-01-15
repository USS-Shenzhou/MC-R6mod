package ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ussshenzhou.rainbow6.Rainbow6;
import ussshenzhou.rainbow6.client.gui.ScreenManager;
import ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton1;

/**
 * @author USS_Shenzhou
 */
@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component pTitle) {
        super(pTitle);
    }

    HoverSensitiveImageButton1 initiateButton = new HoverSensitiveImageButton1(
            new TranslatableComponent("gui.r6ms.pausescreen.button"),
            pButton -> {
                ScreenManager.showNewLayer(new MainMenuScreen());
            },
            new ResourceLocation(Rainbow6.MOD_ID,"textures/gui/button20unhovered18.png"),
            new ResourceLocation(Rainbow6.MOD_ID,"textures/gui/button20hovered.png")
    );

    @Inject(method = "createPauseMenu",at=@At("RETURN"))
    private void addR6MSButton(CallbackInfo ci){
        //TODO Do not add if already opened.
        initiateButton.setPadding(1);
        initiateButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        initiateButton.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        initiateButton.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);

        initiateButton.setAbsBounds(this.width / 2 - 102, this.height / 4 + 144 + -16, 204, 20);
        initiateButton.layout();
        this.addRenderableWidget(initiateButton);
    }

    @Inject(method = "tick",at = @At("RETURN"))
    private void buttonTick(CallbackInfo ci){
        initiateButton.tickT();
    }
}
