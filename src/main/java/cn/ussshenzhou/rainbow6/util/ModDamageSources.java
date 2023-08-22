package cn.ussshenzhou.rainbow6.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

/**
 * @author USS_Shenzhou
 */
public class ModDamageSources {
    public static final ResourceKey<DamageType> SHOT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(R6Constants.MOD_ID, "shot"));

    public static DamageSource shot(@Nonnull Entity from, Entity to) {
        return from.level().damageSources().source(SHOT, from, to);
    }
}
