package cn.ussshenzhou.rainbow6.config;

import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.config.TConfig;

/**
 * @author USS_Shenzhou
 */
public record ConfigControls(
        KeyTrig aim,
        KeyTrig lean,
        KeyTrig sprint,
        KeyTrig crouch,
        KeyTrig prone,
        KeyTrig walk
) implements TConfig {

    public ConfigControls(KeyTrig aim, KeyTrig lean, KeyTrig sprint, KeyTrig crouch, KeyTrig prone, KeyTrig walk) {
        this.aim = aim;
        this.lean = lean;
        this.sprint = sprint;
        this.crouch = crouch;
        this.prone = prone;
        this.walk = walk;
    }

    public ConfigControls() {
        this(KeyTrig.HOLD, KeyTrig.HOLD, KeyTrig.HOLD, KeyTrig.HOLD, KeyTrig.HOLD, KeyTrig.HOLD);
    }

    @Override
    public String getChildDirName() {
        return R6Constants.MOD_ID;
    }
}
