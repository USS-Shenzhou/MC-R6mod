package cn.ussshenzhou.rainbow6.armors.mira;

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
public class MiraArmor extends R6ArmorItem {
    public MiraArmor( EquipmentSlotType slot) {
        super(ModArmorMaterials.LEVEL3, slot);
    }

    @Override
    public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
        return new MiraArmorModel(slot);
    }

    @Nonnull
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "rainbow6:textures/armor/mira3.png";
    }
}
