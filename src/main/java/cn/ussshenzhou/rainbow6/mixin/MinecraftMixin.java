package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

/**
 * @author USS_Shenzhou
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Nullable
    public Screen screen;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V"))
    private void tickAllScreenLayers(Runnable crashreport, String crashreportcategory, String throwable) {
        Screen.wrapScreenError(screen::tick, "Ticking screen", screen.getClass().getCanonicalName());
        for (Screen s : ForgeHooksClientAccessor.getGuiLayers()) {
            if (s instanceof TScreen && s != screen) {
                Screen.wrapScreenError(s::tick, "Ticking screen", s.getClass().getCanonicalName());
            }
        }
    }
}
