package cn.ussshenzhou.rainbow6.gun.entity;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author USS_Shenzhou
 */

public class ModGunEntityTypeRegistry {
    public static DeferredRegister<EntityType<?>> GUN_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, R6Constants.MOD_ID);

    public static RegistryObject<EntityType<TestBulletEntity>> BULLET_ENTITY_TYPE = GUN_ENTITY_TYPES.register("bullet_entity",
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
