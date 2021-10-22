package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.utils.ModItemGroups;
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
    public static FragGrenade fragGrenade = new FragGrenade();
    public static ReinforcementItem reinforcementItem = new ReinforcementItem();
    public static RemoteGasGrenadeItem remoteGasGrenadeItem = new RemoteGasGrenadeItem();
    public static Exploder exploder = new Exploder();
    public static ProximityAlarmItem proximityAlarmItem = new ProximityAlarmItem();
    public static SmokeGrenade smokeGrenade = new SmokeGrenade();
    public static DroneItem droneItem = new DroneItem();
    public static R6CellPhone r6CellPhone = new R6CellPhone();

    public static Item ironBlock = new BlockItem(ModBlocks.ironBlock,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.ironBlock.getRegistryName());

    public static Item oakPlanksFloor = new BlockItem(ModBlocks.oakPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.oakPlanksFloor.getRegistryName());
    public static Item sprucePlanksFloor = new BlockItem(ModBlocks.sprucePlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.sprucePlanksFloor.getRegistryName());
    public static Item birchPlanksFloor = new BlockItem(ModBlocks.birchPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.birchPlanksFloor.getRegistryName());
    public static Item junglePlanksFloor = new BlockItem(ModBlocks.junglePlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.junglePlanksFloor.getRegistryName());
    public static Item acaciaPlanksFloor = new BlockItem(ModBlocks.acaciaPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.acaciaPlanksFloor.getRegistryName());
    public static Item darkOakPlanksFloor = new BlockItem(ModBlocks.darkOakPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.darkOakPlanksFloor.getRegistryName());
    public static Item crimsonPlanksFloor = new BlockItem(ModBlocks.crimsonPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.crimsonPlanksFloor.getRegistryName());
    public static Item warpedPlanksFloor = new BlockItem(ModBlocks.warpedPlanksFloor,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.warpedPlanksFloor.getRegistryName());

    public static Item oakHatch = new BlockItem(ModBlocks.oakHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.oakHatch.getRegistryName());
    public static Item spruceHatch = new BlockItem(ModBlocks.spruceHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.spruceHatch.getRegistryName());
    public static Item birchHatch = new BlockItem(ModBlocks.birchHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.birchHatch.getRegistryName());
    public static Item jungleHatch = new BlockItem(ModBlocks.jungleHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.jungleHatch.getRegistryName());
    public static Item acaciaHatch = new BlockItem(ModBlocks.acaciaHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.acaciaHatch.getRegistryName());
    public static Item darkoakHatch = new BlockItem(ModBlocks.darkoakHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.darkoakHatch.getRegistryName());
    public static Item crimsonHatch = new BlockItem(ModBlocks.crimsonHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.crimsonHatch.getRegistryName());
    public static Item warpedHatch = new BlockItem(ModBlocks.warpedHatch,new Item.Properties().group(ModItemGroups.Main)).setRegistryName(ModBlocks.warpedHatch.getRegistryName());

}
