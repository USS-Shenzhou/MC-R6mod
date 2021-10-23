package cn.ussshenzhou.rainbow6.particles;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModParticleRegistry {
    @SubscribeEvent
    public static void onParticleFactoryReg(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(ModParticleTypeRegistry.BULLET_HOLE_PARTICLE.get(),BulletHoleParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticleTypeRegistry.GAS_SMOKE_PARTICLE.get(),GasSmokeParticleFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticleTypeRegistry.SMOKE_GRENADE_PARTICLE.get(),SmokeGrenadeParticleFactory::new);
    }
}
