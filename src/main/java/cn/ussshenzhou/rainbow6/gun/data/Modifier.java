package cn.ussshenzhou.rainbow6.gun.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

/**
 * @author USS_Shenzhou
 */
public class Modifier {

    //TODO
    public Modifier() {

    }

    public static Modifier read(CompoundTag tag) {
        return new Modifier();
    }

    public void write(CompoundTag tag) {

    }

    public float getDamageDistanceDecayed(FixedProperty property, float distance) {
        int near = property.decayRange().x;
        int far = property.decayRange().y;
        int basicDamage = property.basicDamage();
        //TODO extended barrel 0.8
        float decayFactor = 0.6f;
        if (distance <= near) {
            return basicDamage;
        } else if (distance >= far) {
            return basicDamage * decayFactor;
        }
        return Mth.lerp((distance - near) / (far - near), basicDamage, basicDamage * decayFactor);
    }
}
