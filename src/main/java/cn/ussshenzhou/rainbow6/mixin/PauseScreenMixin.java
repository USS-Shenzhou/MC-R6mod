package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageButton1;
import cn.ussshenzhou.rainbow6.util.R6Constants;
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

/**
 * @author USS_Shenzhou
 */
@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component pTitle) {
        super(pTitle);
    }

    HoverSensitiveImageButton1 initiateR6msButton$r6ms = new HoverSensitiveImageButton1(
            new TranslatableComponent("gui.r6ms.pause_screen.button"),
            pButton -> {
                ScreenManager.openMainMenuScreen();
            },
            new ResourceLocation(R6Constants.MOD_ID,"textures/gui/button20_shadow_unhovered18.png"),
            new ResourceLocation(R6Constants.MOD_ID,"textures/gui/button20_shadow_hovered.png")
    );

    @Inject(method = "createPauseMenu",at=@At("RETURN"))
    private void addR6msButton(CallbackInfo ci){
        //TODO Do not add if already opened. PauseScreen during match needs remake.
        initiateR6msButton$r6ms.setPadding(1);
        initiateR6msButton$r6ms.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        initiateR6msButton$r6ms.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        initiateR6msButton$r6ms.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);

        initiateR6msButton$r6ms.setAbsBounds(this.width / 2 - 102, this.height / 4 + 144 + -16, 204, 20);
        initiateR6msButton$r6ms.layout();
        this.addRenderableWidget(initiateR6msButton$r6ms);
    }

    @Inject(method = "tick",at = @At("RETURN"))
    private void tickR6msButton(CallbackInfo ci){
        initiateR6msButton$r6ms.tickT();
    }
}
