package com.ussshenzhou.rainbow6.bullets;

import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author USS_Shenzhou
 */
public abstract class AbstractBulletItem extends Item {
    public AbstractBulletItem() {
        super(new Properties().group(ModItemGroups.Weapon));
    }
}
