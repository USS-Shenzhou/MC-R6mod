package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.FragGrenadeEntity;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
    private Boolean normal = true;
    private int timeCount = -1;
    private final int MAX_TIME = 100;

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)entityLiving;
            ItemStack itemstack = player.getHeldItem(player.getActiveHand());
            if (timeLeft>1){
                worldIn.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), ModSounds.IMPACT_GRENADE_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                if (!worldIn.isRemote) {
                    FragGrenadeEntity fragGrenadeEntity = new FragGrenadeEntity(ModEntityTypes.fragGrenadeEntityType,player,worldIn);
                    fragGrenadeEntity.setItem(itemstack);
                    if (normal){
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        fragGrenadeEntity.shoot(player.getLookVec().x,player.getLookVec().y,player.getLookVec().z, 0.75F, 0.1F);
                        fragGrenadeEntity.setTimeCountDown(timeLeft);
                        worldIn.addEntity(fragGrenadeEntity);
                    }
                    else {
                        normal = true;
                    }
                }else {
                    this.timeCount = -1;
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
            if (entityLiving instanceof PlayerEntity && ! ((PlayerEntity) entityLiving).abilities.isCreativeMode){
                stack.shrink(1);
                entityLiving.attackEntityFrom(DamageSource.causeExplosionDamage(entityLiving),entityLiving.getMaxHealth());
            }
            worldIn.createExplosion(entityLiving,DamageSource.causeExplosionDamage(entityLiving),null,entityLiving.getPosX(),entityLiving.getPosY(),entityLiving.getPosZ(),2f,false, Explosion.Mode.DESTROY);
        }
        else {
            this.normal = false;
            this.timeCount = -1;
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.MAX_TIME;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        worldIn.playSound(playerIn,playerIn.getPosX(),playerIn.getPosY(),playerIn.getPosZ(),ModSounds.FRAGGRENADE_READY,SoundCategory.PLAYERS,1.0f,1.0f);
        ItemStack stack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        if (worldIn.isRemote){
            this.timeCount = 0;
        }
        return ActionResult.resultConsume(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (double) this.timeCount/this.MAX_TIME;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.isRemote && timeCount>=0){
            timeCount++;
        }
    }
}
