package cn.ussshenzhou.rainbow6.items;

import cn.ussshenzhou.rainbow6.gui.InGameClientProperties;
import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @author USS_Shenzhou
 */
public class R6CellPhone extends Item {
    public R6CellPhone() {
        super(new Properties()
                .group(ModItemGroups.Main)
                .maxStackSize(0)
        );
        this.setRegistryName("r6cellphone");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote){
            InGameClientProperties.isUsingDrone = true;
        }
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }


}
