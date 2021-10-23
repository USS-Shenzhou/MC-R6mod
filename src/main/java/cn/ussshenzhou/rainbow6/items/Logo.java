package cn.ussshenzhou.rainbow6.items;

import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.item.Item;
/**
 * @author USS_Shenzhou
 */
public class Logo extends Item {
    public Logo() {
        super(new Properties().group(ModItemGroups.Main));
        this.setRegistryName("logo");
    }
}
