package com.ussshenzhou.rainbow6.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
/**
 * @author USS_Shenzhou
 */
public class ModEntityTypes {
    public static EntityType nitroCellEntityType = EntityType.Builder.<NitroCellEntity>create(NitroCellEntity::new,EntityClassification.MISC)
            .size(0.25f,0.12f)
            .build("nitrocell")
            .setRegistryName("rainbow6","nitrocell");
    public static EntityType impactGrenadeEntityType = EntityType.Builder.<ImpactGrenadeEntity>create(ImpactGrenadeEntity::new,EntityClassification.MISC)
            .size(0.1f,0.1f)
            .build("impactgrenade")
            .setRegistryName("rainbow6","impactgrenade");
    public static EntityType fragGrenadeEntityType = EntityType.Builder.<FragGrenadeEntity>create(FragGrenadeEntity::new,EntityClassification.MISC)
            .size(0.1f,0.1f)
            .build("fraggrenade")
            .setRegistryName("rainbow6","fraggrenade");
    /*public static EntityType gumineentitytype = EntityType.Builder.<gumineentity>create(gumineentity::new,EntityClassification.MISC)
            .size(0.2f,0.1f)
            .build("gumine")
            .setRegistryName("gumine");*/
    /*public static EntityType RemoteGasGrenadeEntityType = EntityType.Builder.<RemoteGasGrenadeEntity>create(RemoteGasGrenadeEntity::new,EntityClassification.MISC)
            .size(0.1f,0.1f)
            .build("RemoteGasGrenade")
            .setRegistryName("rainbow6","remotegasgrenade");*/
}
