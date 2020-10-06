package com.ussshenzhou.rainbow6.items;

import com.google.common.collect.Maps;
import com.ussshenzhou.rainbow6.entities.FragGrenadeEntity;
import com.ussshenzhou.rainbow6.entities.ImpactGrenadeEntity;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.entities.NitroCellEntity;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author USS_Shenzhou
 */
public class FragGrenade extends Item {
    public FragGrenade() {
        super(new Properties()
                .group(ModItemGroups.Group1)
        );
        this.setRegistryName("fraggrenade");
        ItemModelsProperties.registerProperty(ModItems.fragGrenade,new ResourceLocation("pulling"),(p_210309_0_, p_210309_1_, p_210309_2_) -> {
            return p_210309_2_ != null && p_210309_2_.isHandActive() && p_210309_2_.getActiveItemStack() == p_210309_0_ ? 1.0F : 0.0F;
        });
    }
    protected static final Logger LOGGER = LogManager.getLogger();

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
                    fragGrenadeEntity.shoot(player.getLookVec().x,player.getLookVec().y,player.getLookVec().z, 0.6F, 0.1F);
                    fragGrenadeEntity.setTimeCountDown(timeLeft);
                    worldIn.addEntity(fragGrenadeEntity);
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
            PlayerEntity player = (PlayerEntity)entityLiving;
            ItemStack itemstack = player.getActiveItemStack();
            FragGrenadeEntity fragGrenadeEntity = new FragGrenadeEntity(ModEntityTypes.fragGrenadeEntityType,player,worldIn);
            fragGrenadeEntity.setItem(itemstack);
            fragGrenadeEntity.shoot(player.getLookVec().x,player.getLookVec().y,player.getLookVec().z, 0.6F, 0.1F);
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
