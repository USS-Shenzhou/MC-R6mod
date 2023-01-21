package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.mixin.ForgeHooksClientAccessor;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
public class ScreenManager {
    private static final Stack<TScreen> SCREEN_STACK = new Stack<>();

    public static void showNewLayerOverBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        ForgeHooksClient.pushGuiLayer(Minecraft.getInstance(), screen);
    }

    public static void showNewLayerClearBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        Minecraft.getInstance().setScreen(screen);
    }

    public static void exitCurrentLayer() {
        TScreen currentLayer = SCREEN_STACK.pop();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen == null) {
            return;
        }
        if (currentLayer != minecraft.screen) {
            //TODO error
        }
        if (currentLayer == ForgeHooksClientAccessor.getGuiLayers().peek()) {
            ForgeHooksClient.popGuiLayer(minecraft);
            if (minecraft.screen != SCREEN_STACK.peek()) {
                //TODO
            }
        }

        /*

        if (minecraft.screen != null && minecraft.screen == currentLayer) {
            try {
                minecraft.setScreen(SCREEN_STACK.peek());
            } catch (EmptyStackException ignored) {
                minecraft.setScreen(null);
            }
        }*/
        ForgeHooksClient.popGuiLayer(Minecraft.getInstance());
    }
}
