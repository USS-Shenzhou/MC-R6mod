package cn.ussshenzhou.rainbow6.item.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
public class TestArmor extends BaseR6ArmorItem {
    public TestArmor(Type slotType) {
        super(ModArmorMaterials.LEVEL2, slotType, "test");
    }


    /**
     * Add @OnlyIn(Dist.CLIENT) manually after override.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForType(Type type) {
        return new TestArmorModel(this.type);
    }
}
