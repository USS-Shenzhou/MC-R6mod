package com.ussshenzhou.rainbow6.util;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onBlockReg(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
                ModBlocks.barricade
        );
    }
    @SubscribeEvent
    public static void onItemReg(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                ModItems.barricadeItem,
                ModItems.logo
        );
    }

    @SubscribeEvent
    public static void onSoundsReg(RegistryEvent.Register<SoundEvent> event){
        event.getRegistry().registerAll(
                ModSounds.BARRICADE_BREAK,
                ModSounds.BARRICADE_PLACE
        );
    }
}
