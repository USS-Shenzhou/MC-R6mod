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

    public int getDamage(FixedProperty property, double distance) {
        int near = property.decayRange().x;
        int far = property.decayRange().y;
        int basicDamage = property.basicDamage();
        //TODO 0.8
        float decayFactor = 0.6f;
        if (distance <= near) {
            return basicDamage;
        } else if (distance >= far) {
            return Math.round(basicDamage * decayFactor);
        }
        return Math.round((float) Mth.lerp((distance - near) / (far - near), basicDamage, basicDamage * decayFactor));
    }
}
