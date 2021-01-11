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
public class ReinforcementItem extends BlockItem {
    public ReinforcementItem() {
        super(ModBlocks.reinforcement,new Properties().group(ModItemGroups.Main));
        this.setRegistryName(ModBlocks.reinforcement.getRegistryName());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        this.tryPlace(new BlockItemUseContext(context));
        if (!context.getPlayer().isCreative()){
            //balance
            context.getPlayer().getCooldownTracker().setCooldown(this, 105);
        }
        return ActionResultType.SUCCESS;
    }
}
