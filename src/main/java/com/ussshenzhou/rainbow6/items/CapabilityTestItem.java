package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.capability.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capability.ModCapabilities;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
/**
 * @author USS_Shenzhou
 */
public class CapabilityTestItem extends Item {
    public CapabilityTestItem() {
        super(new Properties().group(ModItemGroups.Group1));
        this.setRegistryName("capabilitytestitem");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote){
            LazyOptional<IR6PlayerCapability> r6PlayerCap = playerIn.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
            r6PlayerCap.ifPresent((cap)->{
                        String operator = cap.getOperator();
                        String r6Team = cap.getR6Team();
                        LogManager.getLogger().info(operator+","+r6Team);
                    }
            );
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
