package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.entities.NitroCellEntity;
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

public class NitroCell extends Item {

    public NitroCell() {
        super(new Properties().group(ModItemGroups.Group1));
        this.setRegistryName("nitrocell");
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        worldIn.playSound((PlayerEntity)null,playerIn.getPosX(),playerIn.getPosY(),playerIn.getPosZ(),ModSounds.NITRO_CELL_THROW,SoundCategory.PLAYERS,1.0f,1.0f);
        if (!worldIn.isRemote) {
            NitroCellEntity nitroCellEntity = new NitroCellEntity(ModEntityTypes.nitroCellEntityType,playerIn,worldIn);
            nitroCellEntity.setItem(itemstack);
            nitroCellEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.6F, 0.7F, 0.1F);
            worldIn.addEntity(nitroCellEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

}

