package com.ussshenzhou.rainbow6.bullets;

import com.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.item.Item;

/**
 * @author USS_Shenzhou
 */
public abstract class AbstractBulletItem extends Item {
    public AbstractBulletItem() {
        super(new Properties()
                .group(ModItemGroups.Weapon)
                .maxStackSize(200)
        );
    }
}
