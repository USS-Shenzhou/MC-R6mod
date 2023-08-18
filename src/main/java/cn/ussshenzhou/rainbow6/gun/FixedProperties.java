package cn.ussshenzhou.rainbow6.gun;

/**
 * @author USS_Shenzhou
 */
public enum FixedProperties {
    TEST(new FixedProperty(Trigger.AUTO, 600));

    private final FixedProperty property;

    FixedProperties(FixedProperty property) {
        this.property = property;
    }

    public FixedProperty getProperty() {
        return property;
    }
}
