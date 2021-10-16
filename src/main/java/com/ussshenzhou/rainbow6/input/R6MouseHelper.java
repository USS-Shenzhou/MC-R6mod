package com.ussshenzhou.rainbow6.input;

import com.ussshenzhou.rainbow6.gui.InGameClientProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class R6MouseHelper {
    public static double mouseX;
    public static double mouseY;
    public static double prevMouseX;
    public static double prevMouseY;
    private static boolean ignoreFirstMove = true;
    public static double xVelocity = 0;
    public static double yVelocity = 0;

    public static void tick() {
        MouseHelper mouseHelper = Minecraft.getInstance().mouseHelper;
        if (ignoreFirstMove) {
            prevMouseX = mouseHelper.getMouseX();
            prevMouseY = mouseHelper.getMouseY();
            ignoreFirstMove = false;
        }
        prevMouseX = mouseX;
        prevMouseY = mouseY;
        mouseX = mouseHelper.getMouseX();
        mouseY = mouseHelper.getMouseY();

        xVelocity = prevMouseX - mouseX;
        yVelocity = prevMouseY - mouseY;

        double d4 = Minecraft.getInstance().gameSettings.mouseSensitivity * (double) 0.6F + (double) 0.2F;
        double d5 = d4 * d4 * d4 * 8.0D;
        double d2 = xVelocity * d5;
        double d3 = yVelocity * d5;

        if (InGameClientProperties.isUsingDrone) {
            try {
                InGameClientProperties.getR6DroneGui().getCurrentDrone().rotateTowards(d2, d3);
            } catch (NullPointerException ignored) {

            }

        }
        xVelocity = 0;
        yVelocity = 0;
    }
}
