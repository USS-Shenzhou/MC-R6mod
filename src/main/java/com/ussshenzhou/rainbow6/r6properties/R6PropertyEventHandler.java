package com.ussshenzhou.rainbow6.r6properties;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class R6PropertyEventHandler {
    @SubscribeEvent
    public static void onPlayerIn(PlayerEvent.PlayerLoggedInEvent event){
        UUID uuid = event.getPlayer().getUniqueID();
    }
}
