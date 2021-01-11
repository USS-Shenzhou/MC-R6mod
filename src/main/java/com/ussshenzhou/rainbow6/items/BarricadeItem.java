package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

/**
 * @author USS_Shenzhou
 */
public class BarricadeItem extends BlockItem {
    public BarricadeItem() {
        super(ModBlocks.barricade,new Properties().group(ModItemGroups.Main));
        this.setRegistryName(ModBlocks.barricade.getRegistryName());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        this.tryPlace(new BlockItemUseContext(context));
        if (!context.getPlayer().isCreative()){
            //balance
            context.getPlayer().getCooldownTracker().setCooldown(this, 50);
        }
        return ActionResultType.SUCCESS;
    }
}
