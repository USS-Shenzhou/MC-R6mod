package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.BlockItem;
/**
 * @author USS_Shenzhou
 */
public class BlackMirrorItem extends BlockItem {
    public BlackMirrorItem() {
        super(ModBlocks.blackMirror,new Properties().group(ModItemGroups.Main));
        this.setRegistryName(ModBlocks.blackMirror.getRegistryName());
    }

}
