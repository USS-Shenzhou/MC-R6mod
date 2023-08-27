package cn.ussshenzhou.rainbow6.util;

import net.minecraft.client.Minecraft;

/**
 * @author USS_Shenzhou
 */
public class ClientUtils {
    static Minecraft mc = Minecraft.getInstance();

    public static boolean isInWorld() {

        return mc.player != null && mc.screen == null && mc.mouseHandler.isMouseGrabbed() && mc.isWindowActive();
    }

    public static boolean isNotInWorld() {
        return !isInWorld();
    }

    public static boolean isOp() {
        return mc.player.hasPermissions(4);
    }

}
