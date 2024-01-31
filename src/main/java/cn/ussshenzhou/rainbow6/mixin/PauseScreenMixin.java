package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageButton1;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author USS_Shenzhou
 */
@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component pTitle) {
        super(pTitle);
    }

    HoverSensitiveImageButton1 r6msInitiateR6msButton = new HoverSensitiveImageButton1(
            Component.translatable("gui.r6ms.pause_screen.button"),
            pButton -> {
                ScreenManager.openMainMenuScreen();
            },
            new ResourceLocation(R6Constants.MOD_ID,"textures/gui/button20_shadow_unhovered18.png"),
            new ResourceLocation(R6Constants.MOD_ID,"textures/gui/button20_shadow_hovered.png")
    );

    @Inject(method = "createPauseMenu",at=@At("RETURN"))
    private void addR6msButton(CallbackInfo ci){
        //TODO Do not add if already opened. PauseScreen during match needs remake.
        r6msInitiateR6msButton.setPadding(1);
        r6msInitiateR6msButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        r6msInitiateR6msButton.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        r6msInitiateR6msButton.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);

        r6msInitiateR6msButton.setAbsBounds(this.width / 2 - 102, this.height / 4 + 144 + -16, 204, 20);
        r6msInitiateR6msButton.layout();
        this.addRenderableWidget(r6msInitiateR6msButton);
    }

    @Inject(method = "tick",at = @At("RETURN"))
    private void tickR6msButton(CallbackInfo ci){
        r6msInitiateR6msButton.tickT();
    }

    @Redirect(method = "createPauseMenu",at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;I)Lnet/minecraft/client/gui/layouts/LayoutElement;",ordinal = 0))
    private <T extends LayoutElement> T r6msRemoveModsButton(GridLayout.RowHelper instance, T pChild, int pOccupiedColumns){
        return null;
    }
}
