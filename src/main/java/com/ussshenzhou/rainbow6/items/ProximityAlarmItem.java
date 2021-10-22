package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.entities.ProximityAlarmEntity;
import com.ussshenzhou.rainbow6.utils.ModItemGroups;
import com.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * @author USS_Shenzhou
 */
public class ProximityAlarmItem extends Item {
    public ProximityAlarmItem() {
        super(new Properties().group(ModItemGroups.Main));
        this.setRegistryName("proximityalarm");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.NITRO_CELL_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!worldIn.isRemote) {
            ProximityAlarmEntity proximityAlarmEntity = new ProximityAlarmEntity(ModEntityTypes.proximityAlarmEntityType,playerIn,worldIn);
            proximityAlarmEntity.setItem(itemstack);
            proximityAlarmEntity.shoot(playerIn.getLookVec().x,playerIn.getLookVec().y,playerIn.getLookVec().z,0.82f,0.1F);
            worldIn.addEntity(proximityAlarmEntity);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
