package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.DroneEntity;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
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
public class DroneItem extends Item {
    public DroneItem() {
        super(new Properties()
                .group(ModItemGroups.Main)
        );
        this.setRegistryName("drone");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        worldIn.playSound(null,playerIn.getPosX(),playerIn.getPosY(),playerIn.getPosZ(), ModSounds.REMOTEGASGRENADE_THROW, SoundCategory.PLAYERS,0.8f,0.9f);
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote){
            DroneEntity drone = new DroneEntity(ModEntityTypes.droneEntityType,playerIn,worldIn);
            drone.setItem(itemStack);
            drone.shoot(playerIn.getLookVec().x,playerIn.getLookVec().y,playerIn.getLookVec().z,0.8f,0.1f);
            worldIn.addEntity(drone);
        }
        if (!playerIn.isCreative()){
            itemStack.shrink(1);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS,itemStack);
    }
}
