package com.ussshenzhou.rainbow6.items;


import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.entities.RemoteGasGrenadeEntity;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import com.ussshenzhou.rainbow6.util.ModSounds;
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
public class RemoteGasGrenadeItem extends Item {
    public RemoteGasGrenadeItem() {
        super(new Properties()
                .group(ModItemGroups.Main)
        );
        this.setRegistryName("remotegasgrenade");
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.REMOTEGASGRENADE_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!worldIn.isRemote) {
            RemoteGasGrenadeEntity remoteGsaGrenadeEntity = new RemoteGasGrenadeEntity(ModEntityTypes.remoteGasGrenadeEntityType,playerIn,worldIn);
            remoteGsaGrenadeEntity.setItem(itemstack);
            remoteGsaGrenadeEntity.shoot(playerIn.getLookVec().x,playerIn.getLookVec().y,playerIn.getLookVec().z, 1.0F, 0.1F);
            remoteGsaGrenadeEntity.setRandomRotation();
            worldIn.addEntity(remoteGsaGrenadeEntity);
        }
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
