package com.ussshenzhou.rainbow6.util;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static ItemGroup Group1 = new ItemGroup("Rainbow6:Minesiege") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.logo);
        }
    };
}
