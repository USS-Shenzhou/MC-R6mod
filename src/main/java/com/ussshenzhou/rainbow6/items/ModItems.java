package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.Barricade;
import com.ussshenzhou.rainbow6.blocks.BlackMirror;
import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
/**
 * @author USS_Shenzhou
 */
public class ModItems {
    public static Logo logo = new Logo();
    public static ImpactGrenade impactGrenade = new ImpactGrenade();
    public static BarricadeItem barricadeItem = new BarricadeItem();
    public static NitroCell nitroCell = new NitroCell();
    public static NitroCellExploder nitroCellExploder = new NitroCellExploder();
    public static BlackMirrorItem blackMirrorItem = new BlackMirrorItem();
    public static CapabilityTestItem capabilityTestItem = new CapabilityTestItem();
    public static FragGrenade fragGrenade = new FragGrenade();
    public static ReinforcementItem reinforcementItem = new ReinforcementItem();
    public static Item ironBlock = new BlockItem(ModBlocks.ironBlock,new Item.Properties().group(ModItemGroups.Group1)).setRegistryName(ModBlocks.ironBlock.getRegistryName());
    public static Item capabilityTestBlockItem = new BlockItem(ModBlocks.capabilityTestBlock,new Item.Properties().group(ModItemGroups.Group1)).setRegistryName(ModBlocks.capabilityTestBlock.getRegistryName());
}
