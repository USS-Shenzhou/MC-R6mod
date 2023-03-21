package cn.ussshenzhou.rainbow6.mixinproxy;

/**
 * @author USS_Shenzhou
 */
public interface FoodDataProxy {
    default boolean isR6msFoodEnabled() {
        return true;
    }

    default void setR6msFoodEnabled(boolean r6msFoodEnabled) {
    }
}
