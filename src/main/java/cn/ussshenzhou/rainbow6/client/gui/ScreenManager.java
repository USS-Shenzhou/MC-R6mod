package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.mixin.ForgeHooksClientAccessor;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
public class ScreenManager {
    private static final Stack<TScreen> SCREEN_STACK = new Stack<>();
    private static Stack<Screen> screenBuffer = new Stack<>();

    //TODO full test

    public static void showNewLayerOverBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        ForgeHooksClient.pushGuiLayer(Minecraft.getInstance(), screen);
    }

    public static void showNewLayerClearBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        Minecraft.getInstance().setScreen(screen);
    }

    public static void exitCurrentLayer() {
        TScreen currentLayer = null;
        TScreen nextLayer = null;
        try {
            currentLayer = SCREEN_STACK.pop();
            nextLayer = SCREEN_STACK.peek();
        } catch (EmptyStackException ignored) {

        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen == null) {
            return;
        }
        if (currentLayer != minecraft.screen) {
            //TODO error
        }
        Screen forgeLayer = null;
        try {
            forgeLayer = ForgeHooksClientAccessor.getGuiLayers().peek();
        } catch (EmptyStackException ignored) {

        }
        if (currentLayer == forgeLayer) {
            ForgeHooksClient.popGuiLayer(minecraft);
            if (minecraft.screen != nextLayer) {
                screenBuffer = ForgeHooksClientAccessor.getGuiLayers();
                minecraft.setScreen(nextLayer);
            }
        } else {
            minecraft.setScreen(nextLayer);
            ForgeHooksClientAccessor.setGuiLayers(screenBuffer);
        }
    }
}
