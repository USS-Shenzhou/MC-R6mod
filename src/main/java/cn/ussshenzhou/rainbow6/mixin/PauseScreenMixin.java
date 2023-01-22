package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.widgets.HoverSensitiveImageButton1;
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
import cn.ussshenzhou.rainbow6.Rainbow6;

/**
 * @author USS_Shenzhou
 */
@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component pTitle) {
        super(pTitle);
    }

    HoverSensitiveImageButton1 initiateR6msButton = new HoverSensitiveImageButton1(
            new TranslatableComponent("gui.r6ms.pausescreen.button"),
            pButton -> {
                ScreenManager.showNewLayerClearBg(new MainMenuScreen());
            },
            new ResourceLocation(Rainbow6.MOD_ID,"textures/gui/button20_shadow_unhovered18.png"),
            new ResourceLocation(Rainbow6.MOD_ID,"textures/gui/button20_shadow_hovered.png")
    );

    @Inject(method = "createPauseMenu",at=@At("RETURN"))
    private void addR6msButton(CallbackInfo ci){
        //TODO Do not add if already opened. PauseScreen during match needs remake.
        initiateR6msButton.setPadding(1);
        initiateR6msButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        initiateR6msButton.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        initiateR6msButton.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);

        initiateR6msButton.setAbsBounds(this.width / 2 - 102, this.height / 4 + 144 + -16, 204, 20);
        initiateR6msButton.layout();
        this.addRenderableWidget(initiateR6msButton);
    }

    @Inject(method = "tick",at = @At("RETURN"))
    private void tickR6msButton(CallbackInfo ci){
        initiateR6msButton.tickT();
    }
}
