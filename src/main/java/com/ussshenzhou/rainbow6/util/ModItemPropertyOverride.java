package com.ussshenzhou.rainbow6.util;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModItemPropertyOverride {
    @SubscribeEvent
    public static void itemPropertyOverride(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            //from BOW's part in ItemModelsProperties.java
            ItemModelsProperties.registerProperty(ModItems.fragGrenade,new ResourceLocation("pulling"),(itemStack, clientWorld, livingEntity) -> {
                return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
            });
        });
    }
}
