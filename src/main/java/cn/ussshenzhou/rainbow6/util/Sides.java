package cn.ussshenzhou.rainbow6.util;

/**
 * @author USS_Shenzhou
 */

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum Sides {
    ATTACKER("gui.r6ms.attacker"),
    DEFENDER("gui.r6ms.defender");

    private String translateKey;

    Sides(String translateKey) {
        this.translateKey = translateKey;
    }

    public String getTranslateKey() {
        return translateKey;
    }
}
