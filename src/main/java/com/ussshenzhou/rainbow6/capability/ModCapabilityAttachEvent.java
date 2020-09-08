package com.ussshenzhou.rainbow6.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class ModCapabilityAttachEvent {
    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity){
            event.addCapability(new ResourceLocation("rainbow6","r6player"),new R6PlayerCapabilityProvider());
        }
    }
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        if (!event.isWasDeath()){
            LazyOptional<IR6PlayerCapability> oldR6Cap = event.getOriginal().getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
            LazyOptional<IR6PlayerCapability> newR6Cap = event.getPlayer().getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
            if (oldR6Cap.isPresent()&& newR6Cap.isPresent()){
                newR6Cap.ifPresent((newCap)->{
                    oldR6Cap.ifPresent((oldCap)->{
                        newCap.deserializeNBT(oldCap.serializeNBT());
                            }

                    );
                        }

                );
            }
        }
    }
}
