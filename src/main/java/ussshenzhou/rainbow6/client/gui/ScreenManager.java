package ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
public class ScreenManager {
    private static final Stack<TScreen> SCREEN_STACK = new Stack<>();

    public static void showNewLayer(TScreen screen) {
        SCREEN_STACK.push(screen);
        Minecraft.getInstance().setScreen(screen);
    }

    public static void exitCurrentLayer() {
        TScreen currentLayer = SCREEN_STACK.pop();
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null && minecraft.screen == currentLayer) {
            try {
                minecraft.setScreen(SCREEN_STACK.peek());
            } catch (EmptyStackException ignored) {
                minecraft.setScreen(null);
            }
        }
    }
}
