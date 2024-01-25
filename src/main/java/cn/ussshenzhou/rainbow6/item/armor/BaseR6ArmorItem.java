package cn.ussshenzhou.rainbow6.item.armor;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.fml.loading.FMLEnvironment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * @author USS_Shenzhou
 * <br/>The idea of armor-with-model came from Botania, by Vazkii at <a href="https://github.com/VazkiiMods/Botania/tree/release-1.16.5-419">https://github.com/VazkiiMods/Botania/tree/release-1.16.5-419</a> in R6MS 0.2.x under MIT.
 * <br/>Now the new armor-with-model system cames from <a href="https://github.com/VazkiiMods/Botania/tree/1.19.x">https://github.com/VazkiiMods/Botania/tree/1.19.x</a>.
 */
public abstract class BaseR6ArmorItem extends ArmorItem {

    protected String name;

    public BaseR6ArmorItem(ModArmorMaterials armorLevel, Type slotType, String name) {
        super(armorLevel, slotType, new Properties());
        this.name = name;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            setModel();
        }
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return R6Constants.MOD_ID + ":textures/armor/" + name + ".png";
    }

    //-----CLIENT-----

    @OnlyIn(Dist.CLIENT)
    protected HumanoidModel<?> model;

    @OnlyIn(Dist.CLIENT)
    private void setModel() {
        this.model = provideArmorModelForType(type);
    }

    /**
     * Add @OnlyIn(Dist.CLIENT) manually after override.
     */
    @OnlyIn(Dist.CLIENT)
    public abstract HumanoidModel<?> provideArmorModelForType(Type type);

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @Nonnull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return model;
            }
        });
    }
}
