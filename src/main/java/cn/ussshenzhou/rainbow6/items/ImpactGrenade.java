package cn.ussshenzhou.rainbow6.items;

import cn.ussshenzhou.rainbow6.entities.ImpactGrenadeEntity;
import cn.ussshenzhou.rainbow6.entities.ModEntityTypes;
import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import cn.ussshenzhou.rainbow6.utils.ModSounds;
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
public class ImpactGrenade extends Item {

    public ImpactGrenade() {
        super(new Properties().group(ModItemGroups.Main));
        this.setRegistryName("impactgrenade");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.IMPACT_GRENADE_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!worldIn.isRemote) {
            ImpactGrenadeEntity impactgrenadeentity = new ImpactGrenadeEntity(ModEntityTypes.impactGrenadeEntityType, playerIn, worldIn);
            impactgrenadeentity.setItem(itemstack);
            impactgrenadeentity.shoot(playerIn.getLookVec().x, playerIn.getLookVec().y, playerIn.getLookVec().z, 0.85F, 0.1F);
            impactgrenadeentity.setRandomRotation();
            worldIn.addEntity(impactgrenadeentity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
