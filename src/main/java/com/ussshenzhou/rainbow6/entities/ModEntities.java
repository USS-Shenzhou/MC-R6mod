package com.ussshenzhou.rainbow6.entities;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;
/**
 * @author USS_Shenzhou
 */
public class ModEntities {
    @ObjectHolder("rainbow6:impactgrenade")
    public static EntityType<ImpactGrenadeEntity> impactGrenadeEntityType;
    @ObjectHolder("rainbow6:fraggrenade")
    public static EntityType<FragGrenadeEntity> fragGrenadeEntityEntityType;
    @ObjectHolder("rainbow6:remotegasgrenade")
    public static EntityType<RemoteGasGrenadeEntity> remoteGasGrenadeEntityEntityType;
    @ObjectHolder("rainbow6:proximityalarm")
    public static EntityType<ProximityAlarmEntity> proximityAlarmEntityEntityType;
    /*@ObjectHolder("rainbow6:gumine")
    public static EntityType<gumineentity> gumine;*/
}
