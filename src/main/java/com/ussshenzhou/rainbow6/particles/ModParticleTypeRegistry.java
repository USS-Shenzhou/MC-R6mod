package com.ussshenzhou.rainbow6.particles;

import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
/**
 * @author USS_Shenzhou
 */
public class ModParticleTypeRegistry {
    /**
     * DO NOT forget reg it in rainbow6.java
     */
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,"rainbow6");

    public static final RegistryObject<ParticleType<GasSmokeParticleData>> GAS_SMOKE_PARTICLE = PARTICLE_TYPE.register("gas_smoke_particle", GasSmokeParticleType::new);
}
