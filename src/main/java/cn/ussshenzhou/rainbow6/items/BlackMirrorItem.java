package cn.ussshenzhou.rainbow6.items;

import cn.ussshenzhou.rainbow6.blocks.ModBlocks;
import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
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
