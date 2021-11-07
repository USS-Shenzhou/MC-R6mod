package cn.ussshenzhou.rainbow6.armors;

import cn.ussshenzhou.rainbow6.armors.ash.AshArmor;
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
}
