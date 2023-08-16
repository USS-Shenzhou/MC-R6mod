package cn.ussshenzhou.rainbow6.gun.item;

import cn.ussshenzhou.rainbow6.gun.GunProperties;
import cn.ussshenzhou.rainbow6.gun.GunProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author USS_Shenzhou
 */
public class TestGun extends Item {

    private final GunProperty property;

    public TestGun(GunProperties property) {
        super(new Properties());
        this.property = property.getProperty();
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    public GunProperty getProperty() {
        return property;
    }
}
