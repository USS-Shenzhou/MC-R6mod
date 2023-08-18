package cn.ussshenzhou.rainbow6.gun;

/**
 * @author USS_Shenzhou
 */
public record FixedProperty(
        Trigger trigger,
        int shotsPerMinute

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
}
