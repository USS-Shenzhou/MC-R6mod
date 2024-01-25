package cn.ussshenzhou.rainbow6.gun.entity;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */

public class ModGunEntityTypeRegistry {
    public static DeferredRegister<EntityType<?>> GUN_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, R6Constants.MOD_ID);

    public static Supplier<EntityType<TestBulletEntity>> BULLET_ENTITY_TYPE = GUN_ENTITY_TYPES.register("bullet_entity",
            () -> EntityType.Builder.of((EntityType<TestBulletEntity> t, Level l) -> new TestBulletEntity(t, l), MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setTrackingRange(0)
                    .noSummon()
                    .noSave()
                    .fireImmune()
                    .setShouldReceiveVelocityUpdates(false)
                    .build(R6Constants.MOD_ID + ":bullet_entity")
    );
}
