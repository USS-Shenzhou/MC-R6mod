package cn.ussshenzhou.rainbow6.gun.data;

/**
 * @author USS_Shenzhou
 */
public enum FixedProperties {
    TEST(new FixedProperty(Trigger.AUTO, 600, 1, Bolt.CLOSED_BOLT, 25));

    private final FixedProperty property;

    FixedProperties(FixedProperty property) {
        this.property = property;
    }

    public FixedProperty getProperty() {
        return property;
    }
}
