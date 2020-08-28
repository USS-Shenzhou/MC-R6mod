package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.Barricade;
import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModItems {
    public static Logo logo = new Logo();
    public static Item barricadeItem = new BlockItem(ModBlocks.barricade,new Item.Properties().group(ModItemGroups.Group1)).setRegistryName(ModBlocks.barricade.getRegistryName());
    public static Item ironBlock = new BlockItem(ModBlocks.ironBlock,new Item.Properties().group(ModItemGroups.Group1)).setRegistryName(ModBlocks.ironBlock.getRegistryName());
}
