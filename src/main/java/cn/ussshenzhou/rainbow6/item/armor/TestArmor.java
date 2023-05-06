package cn.ussshenzhou.rainbow6.item.armor;

import net.minecraft.client.model.HumanoidModel;

/**
 * @author USS_Shenzhou
 */
public class TestArmor extends BaseR6ArmorItem {
    public TestArmor(Type slotType) {
        super(ModArmorMaterials.LEVEL2, slotType, "test");
    }

    @Override
    public HumanoidModel<?> provideArmorModelForType(Type type) {
        return new TestArmorModel(type);
    }
}
