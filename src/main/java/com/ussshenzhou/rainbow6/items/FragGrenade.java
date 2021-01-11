package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.FragGrenadeEntity;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author USS_Shenzhou
 */
public class FragGrenade extends Item {
    public FragGrenade() {
        super(new Properties()
                .group(ModItemGroups.Main)
        );
        this.setRegistryName("fraggrenade");
    }
    protected static final Logger LOGGER = LogManager.getLogger();
    private Boolean normal = true;

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)entityLiving;
            ItemStack itemstack = player.getHeldItem(player.getActiveHand());
            if (timeLeft>1){
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
                worldIn.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.IMPACT_GRENADE_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                if (!worldIn.isRemote) {
                    FragGrenadeEntity fragGrenadeEntity = new FragGrenadeEntity(ModEntityTypes.fragGrenadeEntityType,player,worldIn);
                    fragGrenadeEntity.setItem(itemstack);
                    if (normal){
                        fragGrenadeEntity.shoot(player.getLookVec().x,player.getLookVec().y,player.getLookVec().z, 0.6F, 0.1F);
                        fragGrenadeEntity.setTimeCountDown(timeLeft);
                        worldIn.addEntity(fragGrenadeEntity);
                    }
                    else {
                        normal = true;
                    }
                }
                player.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote){
            this.normal = false;
            PlayerEntity player = (PlayerEntity)entityLiving;
            ItemStack itemstack = player.getActiveItemStack();
            FragGrenadeEntity fragGrenadeEntity = new FragGrenadeEntity(ModEntityTypes.fragGrenadeEntityType,player,worldIn);
            fragGrenadeEntity.setItem(itemstack);
            fragGrenadeEntity.shoot(0,0,0, 1.1F, 0.1F);
            fragGrenadeEntity.setTimeCountDown(0);
            worldIn.addEntity(fragGrenadeEntity);
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 90;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        worldIn.playSound(playerIn,playerIn.getPosX(),playerIn.getPosY(),playerIn.getPosZ(),ModSounds.FRAGGRENADE_READY,SoundCategory.PLAYERS,1.0f,1.0f);
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(stack);
    }

}
