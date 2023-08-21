package cn.ussshenzhou.rainbow6.util;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author USS_Shenzhou
 */
public class Utils {

    @OnlyIn(Dist.CLIENT)
    public static boolean isInWorld() {
        var minecraft = Minecraft.getInstance();
        return minecraft.player != null && minecraft.screen == null && minecraft.mouseHandler.isMouseGrabbed() && minecraft.isWindowActive();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isNotInWorld() {
        return !isInWorld();
    }

    public static void computeTag(CompoundTag tag, String key, Function<@Nullable Integer, Integer> remappingFunction) {
        tag.putInt(key, remappingFunction.apply(tag.contains(key) ? tag.getInt(key) : null));
    }

    public static void computeTag(CompoundTag tag, String key, int defaultValue, Function<@NotNull Integer, Integer> remappingFunction) {
        computeTag(tag, key, i -> {
            if (i == null) {
                return defaultValue;
            } else {
                return remappingFunction.apply(i);
            }
        });
    }
}
