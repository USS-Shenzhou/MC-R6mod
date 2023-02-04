package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
@Mixin(value = ForgeHooksClient.class, remap = false)
public interface ForgeHooksClientAccessor {


    @Mutable
    @Accessor
    static void setGuiLayers(Stack<Screen> guiLayers) {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static Stack<Screen> getGuiLayers() {
        throw new UnsupportedOperationException();
    }
}
