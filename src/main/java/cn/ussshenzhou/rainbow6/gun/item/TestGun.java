package cn.ussshenzhou.rainbow6.gun.item;

import cn.ussshenzhou.rainbow6.gun.FixedProperties;
import cn.ussshenzhou.rainbow6.gun.FixedProperty;
import cn.ussshenzhou.rainbow6.gun.Modifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author USS_Shenzhou
 */
public class TestGun extends Item {

    private final FixedProperty property;
    private static final String TAG_BULLETS = "bullets";
    private static final String TAG_MODIFIER = "modifier";

    public TestGun(FixedProperties property) {
        super(new Properties());
        this.property = property.getProperty();
    }

    /*@Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }*/

    public FixedProperty getFixedProperty() {
        return property;
    }

    public Modifier getModifier(ItemStack stack) {
        return Modifier.read(stack.getOrCreateTag().getCompound(TAG_MODIFIER));
    }
}
