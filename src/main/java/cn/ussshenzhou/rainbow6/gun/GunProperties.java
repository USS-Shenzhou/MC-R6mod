package cn.ussshenzhou.rainbow6.gun;

/**
 * @author USS_Shenzhou
 */
public enum GunProperties {
    TEST(new GunProperty(600));

    private final GunProperty property;

    GunProperties(GunProperty property) {
        this.property = property;
    }

    public GunProperty getProperty() {
        return property;
    }
}
