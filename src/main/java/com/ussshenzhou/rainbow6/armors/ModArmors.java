package com.ussshenzhou.rainbow6.armors;

import com.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

/**
 * @author USS_Shenzhou
 */
public class ModArmors {
    public static Item ashHead = new ArmorItem(ModArmorMaterials.LEVEL1, EquipmentSlotType.HEAD,new Item.Properties().group(ModItemGroups.Armor)).setRegistryName("ash_head");
    public static Item ashChest = new ArmorItem(ModArmorMaterials.LEVEL1 ,EquipmentSlotType.CHEST,new Item.Properties().group(ModItemGroups.Armor)).setRegistryName("ash_chest");
}
