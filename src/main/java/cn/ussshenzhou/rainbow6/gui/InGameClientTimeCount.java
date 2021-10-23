package cn.ussshenzhou.rainbow6.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

/**
 * @author USS_Shenzhou
 */
public class InGameClientTimeCount {
    private static long matchMakingTime = 0;

    public static long getMatchMakingTime() {
        return Util.milliTime() - matchMakingTime;
    }

    public static void increaseMatchMakingTime() {
        matchMakingTime++;
    }

    public static void resetMatchMakingTime() {
        matchMakingTime = Util.milliTime();
    }

    public static String getMatchMakingSecAsString() {
        return Integer.toString(((int) ((Util.milliTime() - matchMakingTime) / 1000)) % 60);
    }

    public static String getMatchMakingMinAsString() {
        return Integer.toString((int) ((Util.milliTime() - matchMakingTime) / 1000 / 60));
    }
}
