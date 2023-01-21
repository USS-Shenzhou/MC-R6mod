package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
@Mixin(ForgeHooksClient.class)
public interface ForgeHooksClientAccessor {
    @Accessor
    static Stack<Screen> getGuiLayers() {
        throw new UnsupportedOperationException();
    }
}
