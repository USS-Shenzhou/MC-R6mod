package cn.ussshenzhou.rainbow6.bullets;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

/**
 * @author USS_Shenzhou
 */
public class ModBulletEntityTypes {
    public static EntityType testbulletEntityType = EntityType.Builder.<TestBulletEntity>create(TestBulletEntity::new, EntityClassification.MISC)
            .size(0.001f,0.001f)
            .build("testbullet")
            .setRegistryName("rainbow6:testbullet");

}
