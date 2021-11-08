package cn.ussshenzhou.rainbow6.armors;

import cn.ussshenzhou.rainbow6.armors.alex.AlexArmor;
import cn.ussshenzhou.rainbow6.armors.ash.AshArmor;
import cn.ussshenzhou.rainbow6.armors.mira.MiraArmor;
import cn.ussshenzhou.rainbow6.armors.test.TestArmor;
import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

/**
 * @author USS_Shenzhou
 */
public class ModArmors {
    public static Item ashHelmet = new AshArmor(EquipmentSlotType.HEAD).setRegistryName("ash_helmet");
    
    public static Item testHelmet = new TestArmor(EquipmentSlotType.HEAD).setRegistryName("test_helmet");
    public static Item testChestplate = new TestArmor(EquipmentSlotType.CHEST).setRegistryName("test_chestplate");
    public static Item testLeggings = new TestArmor(EquipmentSlotType.LEGS).setRegistryName("test_leggings");
    public static Item testBoots = new TestArmor(EquipmentSlotType.FEET).setRegistryName("test_boots");

    public static Item miraHelmet = new MiraArmor(EquipmentSlotType.HEAD).setRegistryName("mira_helmet");
    public static Item miraChestplate = new MiraArmor(EquipmentSlotType.CHEST).setRegistryName("mira_chestplate");
    public static Item miraLeggings = new MiraArmor(EquipmentSlotType.LEGS).setRegistryName("mira_leggings");
    public static Item miraBoots = new MiraArmor(EquipmentSlotType.FEET).setRegistryName("mira_boots");

    public static Item alexHelmet = new AlexArmor(EquipmentSlotType.HEAD).setRegistryName("alex_helmet");
    public static Item alexChestplate = new AlexArmor(EquipmentSlotType.CHEST).setRegistryName("alex_chestplate");
    public static Item alexLeggings = new AlexArmor(EquipmentSlotType.LEGS).setRegistryName("alex_leggings");
    public static Item alexBoots = new AlexArmor(EquipmentSlotType.FEET).setRegistryName("alex_boots");
}
