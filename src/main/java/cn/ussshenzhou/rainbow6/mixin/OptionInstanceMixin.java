package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.event.OptionInstanceChangeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author USS_Shenzhou
 */
@Mixin(OptionInstance.class)
public class OptionInstanceMixin<T> {

    @Inject(method = "set", at = @At("HEAD"))
    private void r6msFireChangeEvent(T pValue, CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new OptionInstanceChangeEvent<>(((OptionInstance<T>) (Object) this), pValue));
    }
}
