package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.t88.gui.util.ToTranslatableString;

/**
 * @author USS_Shenzhou
 */

public enum KeyTrig implements ToTranslatableString {
    TOGGLE("gui.r6ms.options.key_trig.toggle"),
    HOLD("gui.r6ms.options.key_trig.hold");

    private final String translateKey;

    KeyTrig(String translateKey) {
        this.translateKey = translateKey;
    }

    @Override
    public String translateKey() {
        return translateKey;
    }
}
