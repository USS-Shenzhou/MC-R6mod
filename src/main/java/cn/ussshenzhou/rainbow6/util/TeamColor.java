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

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public int getARGB() {
        if (this == BLUE) {
            return R6Constants.BLUE_STD_ARGB;
        }
        return R6Constants.ORANGE_STD_ARGB;
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public int getRGB() {
        if (this == BLUE) {
            return R6Constants.BLUE_STD_RGB;
        }
        return R6Constants.ORANGE_STD_RGB;
    }
}
