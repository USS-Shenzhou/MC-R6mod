package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

/**
 * @author USS_Shenzhou
 */
public class CapabilityTestItem extends Item {
    public CapabilityTestItem() {
        super(new Properties().group(ModItemGroups.Main));
        this.setRegistryName("capabilitytestitem");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote){
            LazyOptional<IR6PlayerCapability> r6PlayerCap = playerIn.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
            IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
            if ("smoke".equals(ir6PlayerCapability.getOperator())){
                ir6PlayerCapability.setOperator("none");
                playerIn.sendMessage(new StringTextComponent("Successfully switch smoke to none"),playerIn.getUniqueID());
            }
            else{
                ir6PlayerCapability.setOperator("smoke");
                playerIn.sendMessage(new StringTextComponent("Successfully switch none to smoke"),playerIn.getUniqueID());
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
