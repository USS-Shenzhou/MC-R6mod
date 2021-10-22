package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.entities.SmokeGrenadeEntity;
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
public class SmokeGrenade extends Item {
    public SmokeGrenade() {
        super(new Properties()
        .group(ModItemGroups.Main)
        );
        this.setRegistryName("smokegrenade");
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        //Sound
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.IMPACT_GRENADE_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!worldIn.isRemote) {
            SmokeGrenadeEntity smokeGrenadeEntity = new SmokeGrenadeEntity(ModEntityTypes.smokeGrenadeEntityType,playerIn,worldIn);
            smokeGrenadeEntity.setItem(itemstack);
            smokeGrenadeEntity.shoot(playerIn.getLookVec().x,playerIn.getLookVec().y,playerIn.getLookVec().z, 0.8F, 0.1F);
            smokeGrenadeEntity.setRandomRotation();
            worldIn.addEntity(smokeGrenadeEntity);
        }
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
