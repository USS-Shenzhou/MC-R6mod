package cn.ussshenzhou.rainbow6;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.ForgeGameTestHooks;

/**
 * @author USS_Shenzhou
 */
@Mod("r6ms")
public class Rainbow6 {
    public static final boolean TEST = ForgeGameTestHooks.isGametestEnabled();

    public Rainbow6() {
        MinecraftForge.EVENT_BUS.register(this);
        System.setProperty("t88.test_screen_override","cn.ussshenzhou.rainbow6.client.gui.screens.RoundPrepareScreen");
    }
}
