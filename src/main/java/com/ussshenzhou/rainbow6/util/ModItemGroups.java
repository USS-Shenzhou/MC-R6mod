package com.ussshenzhou.rainbow6.util;

import com.ussshenzhou.rainbow6.armors.ModArmors;
import com.ussshenzhou.rainbow6.bullets.ModBulletItems;
import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
/**
 * @author USS_Shenzhou
 */
public class ModItemGroups {
    public static ItemGroup Main = new ItemGroup("Rainbow6:main") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.logo);
        }
    };
    public static ItemGroup Weapon = new ItemGroup("Rainbow6:weapon") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBulletItems.testBulletItem);
        }
    };
    public static ItemGroup Armor = new ItemGroup("Rainbow6:armor") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModArmors.ashChest);
        }
    };
}
