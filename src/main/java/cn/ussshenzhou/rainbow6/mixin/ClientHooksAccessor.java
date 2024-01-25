package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
@Mixin(value = ClientHooks.class, remap = false)
public interface ClientHooksAccessor {

    @Mutable
    @Accessor
    //TODO check usage
    static void setGuiLayers(Stack<Screen> guiLayers) {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static Stack<Screen> getGuiLayers() {
        throw new UnsupportedOperationException();
    }
}
