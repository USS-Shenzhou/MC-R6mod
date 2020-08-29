package com.ussshenzhou.rainbow6.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntityTypes {
    /*public static EntityType c4entitytype = EntityType.Builder.<c4entity>create(c4entity::new,EntityClassification.MISC)
            .size(0.25f,0.12f)
            .build("c4")
            .setRegistryName("rainbow6","c4");*/
    public static EntityType impactgrenadeentitytype = EntityType.Builder.<ImpactGrenadeEntity>create(ImpactGrenadeEntity::new,EntityClassification.MISC)
            .size(0.15f,0.15f)
            .build("impactgrenade")
            .setRegistryName("rainbow6","impactgrenade");
    /*public static EntityType gumineentitytype = EntityType.Builder.<gumineentity>create(gumineentity::new,EntityClassification.MISC)
            .size(0.2f,0.1f)
            .build("gumine")
            .setRegistryName("gumine");*/
    /*public static EntityType RemoteGasGrenadeEntityType = EntityType.Builder.<RemoteGasGrenadeEntity>create(RemoteGasGrenadeEntity::new,EntityClassification.MISC)
            .size(0.1f,0.1f)
            .build("RemoteGasGrenade")
            .setRegistryName("rainbow6","remotegasgrenade");*/
}
