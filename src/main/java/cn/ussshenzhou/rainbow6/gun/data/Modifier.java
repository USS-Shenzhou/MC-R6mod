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
        if (property.isShotgun()) {
            return getDamageDistanceDecayedShotgun(property, distance);
        } else {
            return getDamageDistanceDecayedNormal(property, distance);
        }
    }

    private float getDamageDistanceDecayedNormal(FixedProperty property, float distance) {
        int near = property.decayRange().x;
        int far = property.decayRange().y;
        int basicDamage = property.basicDamage();
        //TODO extended barrel 0.12, 0.8
        float decayFactor = 0.6f;
        if (distance <= near) {
            return basicDamage;
        } else if (distance >= far) {
            return basicDamage * decayFactor;
        }
        return Mth.lerp((distance - near) / (far - near), basicDamage, basicDamage * decayFactor);
    }

    private float getDamageDistanceDecayedShotgun(FixedProperty property, float distance) {
        int basicDamage = property.basicDamage();
        if (distance <= 5) {
            return basicDamage;
        } else if (distance <= 6) {
            return Mth.lerp(distance - 5, basicDamage, basicDamage * 0.75f);
        } else if (distance <= 10) {
            return basicDamage * 0.75f;
        } else if (distance <= 13) {
            return Mth.lerp((distance - 10) / 3, basicDamage * 0.75f, basicDamage * 0.45f);
        } else {
            return basicDamage * 0.45f;
        }
    }
}
