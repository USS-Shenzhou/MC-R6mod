package com.ussshenzhou.rainbow6.bullets.penetrate;

import com.ussshenzhou.rainbow6.network.BulletHoleParticlePack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpecialBlocksMapRegister {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        IsPenetrableBlocks.createMap();
    }
}
