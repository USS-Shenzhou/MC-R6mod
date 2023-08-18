package cn.ussshenzhou.rainbow6.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
public class ClientUtils {

    @OnlyIn(Dist.CLIENT)
    public static boolean isInWorld() {
        var minecraft = Minecraft.getInstance();
        return minecraft.player != null && minecraft.screen == null && minecraft.mouseHandler.isMouseGrabbed() && minecraft.isWindowActive();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isNotInWorld() {
        return !isInWorld();
    }
}
