package com.ussshenzhou.rainbow6.items;

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
    public static RemoteGasGrenadeItem remoteGasGrenadeItem = new RemoteGasGrenadeItem();
    public static Exploder exploder = new Exploder();
    public static Item ironBlock = new BlockItem(ModBlocks.ironBlock,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.ironBlock.getRegistryName());
    public static Item capabilityTestBlockItem = new BlockItem(ModBlocks.capabilityTestBlock,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.capabilityTestBlock.getRegistryName());

}
