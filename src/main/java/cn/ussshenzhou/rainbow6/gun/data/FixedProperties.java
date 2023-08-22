package cn.ussshenzhou.rainbow6.gun.data;

import org.joml.Vector2i;

import static cn.ussshenzhou.rainbow6.gun.data.FixedProperties.Decays.*;

/**
 * @author USS_Shenzhou
 */
public enum FixedProperties {
    TEST(new FixedProperty(Trigger.AUTO, 600, 1, Bolt.CLOSED_BOLT, 25, 8, ASSAULT_RIFLE));

    private final FixedProperty property;

    FixedProperties(FixedProperty property) {
        this.property = property;
    }

    public FixedProperty getProperty() {
        return property;
    }

    public static class Decays {
        public static final Vector2i ASSAULT_RIFLE = new Vector2i(25, 35);
    }
}
