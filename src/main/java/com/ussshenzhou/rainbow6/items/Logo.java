package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.Item;
/**
 * @author USS_Shenzhou
 */
public class Logo extends Item {
    public Logo() {
        super(new Properties().group(ModItemGroups.Group1));
        this.setRegistryName("logo");
    }
}
