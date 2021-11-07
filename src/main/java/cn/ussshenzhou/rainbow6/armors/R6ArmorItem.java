package cn.ussshenzhou.rainbow6.armors;

import cn.ussshenzhou.rainbow6.utils.ModItemGroups;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nonnull;

/**
 * @author USS_Shenzhou
 *
 * The idea/methods/structure of armor-with-model comes from Botania. Visit their repo at
 * @link https://github.com/VazkiiMods/Botania/tree/release-1.16.5-419
 */
public abstract class R6ArmorItem extends ArmorItem {
    private final LazyValue<BipedModel<?>> model;
    public final EquipmentSlotType slot;

    public R6ArmorItem(ModArmorMaterials material, EquipmentSlotType slot) {
        super(material, slot, new Item.Properties().group(ModItemGroups.Armor));
        this.slot = slot;
        this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyValue<>(() -> this.provideArmorModelForSlot(slot)),
                () -> () -> null);
    }

    /**
     * @return return your armor-model extends ModelArmor.
     */
    @OnlyIn(Dist.CLIENT)
    public abstract BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot);

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original) {
        return (A) model.getValue();
    }

    /**
     *
     * @param stack
     * @param entity
     * @param slot
     * @param type
     * @return "rainbow6:textures/armor/ash.png"
     */
    @Nonnull
    @Override
    public abstract String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type);
}
