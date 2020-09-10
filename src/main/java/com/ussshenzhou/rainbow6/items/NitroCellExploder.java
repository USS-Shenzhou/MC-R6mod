package com.ussshenzhou.rainbow6.items;

import com.ussshenzhou.rainbow6.entities.NitroCellEntity;
import com.ussshenzhou.rainbow6.util.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;
/**
 * @author USS_Shenzhou
 */
public class NitroCellExploder extends Item {
    public NitroCellExploder(){
        super(new Properties().group(ModItemGroups.Group1));
        this.setRegistryName("nitrocell_exploder");
    }
    private static final Predicate<NitroCellEntity> ifexist = new Predicate<NitroCellEntity>() {
        @Override
        public boolean test(NitroCellEntity c4entity) {
            return c4entity != null ;
        }
    };
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        List<NitroCellEntity> list = worldIn.getEntitiesWithinAABB(NitroCellEntity.class, playerIn.getBoundingBox().grow(50.0d),ifexist);
        if (!list.isEmpty()){
            for(NitroCellEntity nitroCellEntity:list){
                nitroCellEntity.exploder();
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}