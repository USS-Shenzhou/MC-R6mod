package cn.ussshenzhou.rainbow6.config;

import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.config.TConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.Minecraft;

/**
 * @author USS_Shenzhou
 */
public class Control implements TConfig {
    @SerializedName("aim")
    public KeyTrig aim = KeyTrig.HOLD;

    @SerializedName("lean")
    public KeyTrig lean = KeyTrig.HOLD;

    @Expose(serialize = false, deserialize = false)
    public KeyTrig sprint = Minecraft.getInstance().options.toggleSprint().get() ? KeyTrig.TOGGLE : KeyTrig.HOLD;

    @Expose(serialize = false, deserialize = false)
    public KeyTrig crouch = Minecraft.getInstance().options.toggleCrouch().get() ? KeyTrig.TOGGLE : KeyTrig.HOLD;

    @SerializedName("prone")
    public KeyTrig prone = KeyTrig.HOLD;

    @SerializedName("walk")
    public KeyTrig walk = KeyTrig.HOLD;

    public Control() {
    }

    @Override
    public String getChildDirName() {
        return R6Constants.MOD_ID;
    }
}
