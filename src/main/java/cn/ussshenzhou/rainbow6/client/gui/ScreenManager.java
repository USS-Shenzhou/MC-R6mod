package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.client.gui.screen.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.hud.PlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.mixin.ClientHooksAccessor;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ScreenManager {
    private static final Stack<TScreen> SCREEN_STACK = new Stack<>();
    public static MainMenuScreen mainMenuScreen = null;
    private static Stack<Screen> screenBuffer = new Stack<>();
    public static PlayerInfoBarHud playerInfoBarHud = null;

    public static void showNewLayerOverBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        ClientHooks.pushGuiLayer(Minecraft.getInstance(), screen);
        initResizeScreen(screen);
    }

    public static void showNewLayerClearBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        Minecraft.getInstance().setScreen(screen);
        initResizeScreen(screen);
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
            forgeLayer = ClientHooksAccessor.getGuiLayers().peek();
        } catch (EmptyStackException ignored) {

        }
        if (currentLayer == forgeLayer) {
            ClientHooks.popGuiLayer(minecraft);
            if (minecraft.screen != nextLayer) {
                screenBuffer = ClientHooksAccessor.getGuiLayers();
                minecraft.setScreen(nextLayer);
            }
        } else {
            minecraft.setScreen(nextLayer);
            ClientHooksAccessor.setGuiLayers(screenBuffer);
        }
        if (nextLayer != null) {
            initResizeScreen(nextLayer);
        }
    }

    public static void clearLayers() {
        while (!SCREEN_STACK.isEmpty()) {
            exitCurrentLayer();
        }
    }

    public static void initResizeScreen(TScreen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        screen.resize(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
    }

    public static TScreen getCurrentLayer() {
        return SCREEN_STACK.peek();
    }

    @SubscribeEvent
    public static void onPlayerOut(ClientPlayerNetworkEvent.LoggingOut event) {
        SCREEN_STACK.clear();
        mainMenuScreen = null;
        screenBuffer.clear();
    }

    public static void openMainMenuScreen() {
        if (mainMenuScreen == null) {
            mainMenuScreen = new MainMenuScreen();
            showNewLayerClearBg(mainMenuScreen);
        } else {
            showNewLayerClearBg(mainMenuScreen);
            HudManager.remove(mainMenuScreen.queuingForMatchBar);
        }
    }
}
