package cn.ussshenzhou.rainbow6.item.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author USS_Shenzhou
 */

public enum ModArmorMaterials implements ArmorMaterial {
    LEVEL1("rainbow6:level1", 1, 3, 3, 1, Ingredient.of(Items.LEATHER)),
    LEVEL2("rainbow6:level2", 2, 4, 3, 1, Ingredient.of(Items.IRON_INGOT)),
    LEVEL3("rainbow6:level3", 2, 5, 4, 2, Ingredient.of(Items.NETHERITE_INGOT));

    private final String name;
    private final int helmetArmor;
    private final int chestArmor;
    private final int legArmor;
    private final int bootArmor;
    private final Ingredient repairIngredient;

    ModArmorMaterials(String name, int helmetArmor, int chestArmor, int legArmor, int bootArmor, Ingredient repairIngredient) {
        this.name = name;
        this.helmetArmor = helmetArmor;
        this.chestArmor = chestArmor;
        this.legArmor = legArmor;
        this.bootArmor = bootArmor;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type p_266807_) {
        var equalMaterial = switch (this) {
            case LEVEL1 -> ArmorMaterials.LEATHER;
            case LEVEL2 -> ArmorMaterials.IRON;
            case LEVEL3 -> ArmorMaterials.NETHERITE;
        };
        return equalMaterial.getDurabilityForType(p_266807_);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type p_267168_) {
        return switch (p_267168_) {
            case HELMET -> this.helmetArmor;
            case CHESTPLATE -> this.chestArmor;
            case LEGGINGS -> this.legArmor;
            case BOOTS -> this.bootArmor;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
