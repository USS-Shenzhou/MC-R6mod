package cn.ussshenzhou.rainbow6.config;

import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.config.TConfig;

/**
 * @author USS_Shenzhou
 */
public class ConfigControls implements TConfig {
    public KeyTrig aim = KeyTrig.HOLD;
    public KeyTrig lean = KeyTrig.HOLD;
    public KeyTrig sprint = KeyTrig.HOLD;
    public KeyTrig crouch = KeyTrig.HOLD;
    public KeyTrig prone = KeyTrig.HOLD;
    public KeyTrig walk = KeyTrig.HOLD;

    public ConfigControls() {
    }

    @Override
    public String getChildDirName() {
        return R6Constants.MOD_ID;
    }
}
