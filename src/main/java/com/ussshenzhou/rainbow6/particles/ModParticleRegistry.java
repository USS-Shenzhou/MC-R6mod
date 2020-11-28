package com.ussshenzhou.rainbow6.particles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModParticleRegistry {
    @SubscribeEvent
    public static void onParticleFactoryReg(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(ModParticleTypeRegistry.GAS_SMOKE_PARTICLE.get(),GasSmokeParticleFactory::new);
    }
}
