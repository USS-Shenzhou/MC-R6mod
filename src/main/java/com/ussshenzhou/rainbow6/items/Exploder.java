package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.NitroCellEntity;
import com.ussshenzhou.rainbow6.entities.RemoteGasGrenadeEntity;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
public class Exploder extends Item {
    public Exploder() {
        super(new Properties().group(ModItemGroups.Group1));
        this.setRegistryName("exploder");
    }
    private static final Predicate<RemoteGasGrenadeEntity> ifexist = new Predicate<RemoteGasGrenadeEntity>() {
        @Override
        public boolean test(RemoteGasGrenadeEntity entity) {
            return entity != null ;
        }
    };
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        List<RemoteGasGrenadeEntity> list = worldIn.getEntitiesWithinAABB(RemoteGasGrenadeEntity.class, playerIn.getBoundingBox().grow(50.0d),ifexist);
        if (!list.isEmpty()){
            for(RemoteGasGrenadeEntity entity:list){
                entity.explode();
            }
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), ModSounds.EXPLODER_CLICK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
