package cn.ussshenzhou.rainbow6.gun.data;

import org.joml.Vector2i;

/**
 * @author USS_Shenzhou
 */
public record FixedProperty(
        Trigger trigger,
        int shotsPerMinute,
        int pellets,
        Bolt bolt,
        int magazineCapacity,
        int basicDamage,
        Vector2i decayRange
) {

    public float shotsPerTick() {
        return shotsPerMinute / 60f / 20;
    }

    /*public float getCoolDownPartialTick() {
        return 60 * 20f / shotsPerMinute;
    }*/

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public float coolDownMS() {
        return 60 * 1000f / shotsPerMinute;
    }

    public boolean isShotgun() {
        return pellets > 1;
    }
}
