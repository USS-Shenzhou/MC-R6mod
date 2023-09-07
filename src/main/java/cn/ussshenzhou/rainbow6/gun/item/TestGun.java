package cn.ussshenzhou.rainbow6.gun.item;

import cn.ussshenzhou.rainbow6.gun.data.Bolt;
import cn.ussshenzhou.rainbow6.gun.data.FixedProperties;
import cn.ussshenzhou.rainbow6.gun.data.FixedProperty;
import cn.ussshenzhou.rainbow6.gun.data.Modifier;
import cn.ussshenzhou.rainbow6.util.Utils;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author USS_Shenzhou
 */
public class TestGun extends Item {
    public static final String TAG_BULLETS = "bullets";
    public static final String TAG_BULLET_IN_BOLT = "bullet_in_bolt";

    private final FixedProperty property;

    public TestGun(FixedProperties property) {
        super(new Properties()
                .stacksTo(1)
        );
        this.property = property.getProperty();
    }

    public void additionalTagOnCreativeTab(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        tag.putInt(TAG_BULLETS, property.magazineCapacity());
        tag.putInt(TAG_BULLET_IN_BOLT, property.bolt() == Bolt.CLOSED_BOLT ? 1 : 0);
    }

    public FixedProperty getFixedProperty() {
        return property;
    }

    public Modifier getModifier(ItemStack stack) {
        return Modifier.read(stack.getOrCreateTag());
    }

    public boolean hasAmmoToShoot(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        return property.bolt() == Bolt.CLOSED_BOLT ? tag.getInt(TAG_BULLET_IN_BOLT) > 0 : tag.getInt(TAG_BULLETS) > 0;
    }

    public void consumeOneAmmo(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        Utils.computeTag(tag, TAG_BULLETS, 0, i -> {
            if (i == 0) {
                //final one bullet in closed bolt.
                tag.putInt(TAG_BULLET_IN_BOLT, 0);
                return 0;
            } else {
                return i - 1;
            }
        });
        //TODO remind player to change
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        return (int) Math.max(tag.getInt(TAG_BULLET_IN_BOLT), 13f * tag.getInt(TAG_BULLETS) / property.magazineCapacity());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        return Mth.hsvToRgb(Math.max(0, (float) tag.getInt(TAG_BULLETS) / property.magazineCapacity() / 3),
                1.0F, 1.0F);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
