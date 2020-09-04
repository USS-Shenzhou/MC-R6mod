package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.BlockItem;

public class BlackMirrorItem extends BlockItem {
    public BlackMirrorItem() {
        super(ModBlocks.blackMirror,new Properties().group(ModItemGroups.Group1));
        this.setRegistryName(ModBlocks.blackMirror.getRegistryName());
    }
}
