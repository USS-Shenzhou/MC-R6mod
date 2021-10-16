package com.ussshenzhou.rainbow6.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkRegister {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(BulletHoleParticlePackSend::registerMessage);
        event.enqueueWork(MatchMakingPackSend::registerMessage);
        event.enqueueWork(MatchPlayersPackSend::registerMessage);
        event.enqueueWork(PlayerOperatorPackSend::registerMessage);
        event.enqueueWork(MapScenePackSend::registerMessage);
        event.enqueueWork(SpawnPointPackSend::registerMessage);
        event.enqueueWork(R6DroneMotionPackSend::registerMessage);
        event.enqueueWork(R6DroneControllerPackSend::registerMessage);
    }
}
