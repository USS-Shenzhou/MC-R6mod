package cn.ussshenzhou.rainbow6.util;

/**
 * @author USS_Shenzhou
 */

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum TeamColor {
    BLUE,
    ORANGE;

    public TeamColor opposite() {
        if (this == BLUE) {
            return ORANGE;
        }
        return BLUE;
    }
}
