package cn.ussshenzhou.rainbow6.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * @author USS_Shenzhou
 */
public class Utils {
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

    public static ResourceLocation toResourceLocation(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public static EntityType<?> toEntityType(ResourceLocation location){
        return BuiltInRegistries.ENTITY_TYPE.get(location);
    }
}
