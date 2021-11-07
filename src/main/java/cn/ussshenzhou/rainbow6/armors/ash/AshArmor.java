package cn.ussshenzhou.rainbow6.armors.ash;

import cn.ussshenzhou.rainbow6.armors.ModArmorMaterials;
import cn.ussshenzhou.rainbow6.armors.R6ArmorItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author USS_Shenzhou
 */
public class AshArmor extends R6ArmorItem {
    public AshArmor(EquipmentSlotType slot) {
        super(ModArmorMaterials.LEVEL1, slot);
    }

    @Override
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new AshArmorModel(slot);
    }

    @Nonnull
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "rainbow6:textures/armor/ash.png";
    }
}
