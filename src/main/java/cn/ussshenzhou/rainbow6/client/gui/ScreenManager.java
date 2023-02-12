package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.widgets.PlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.mixin.ForgeHooksClientAccessor;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ScreenManager {
    private static final Stack<TScreen> SCREEN_STACK = new Stack<>();
    public static MainMenuScreen mainMenuScreenBuffer = null;
    private static Stack<Screen> screenBuffer = new Stack<>();
    public static PlayerInfoBarHud playerInfoBarHud = null;

    public static void showNewLayerOverBg(TScreen screen) {
        SCREEN_STACK.push(screen);
        ForgeHooksClient.pushGuiLayer(Minecraft.getInstance(), screen);
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

    @SubscribeEvent
    public static void onPlayerOut(PlayerEvent.PlayerLoggedOutEvent event) {
        SCREEN_STACK.clear();
        mainMenuScreenBuffer = null;
        screenBuffer.clear();
    }

    public static void openMainMenuScreen() {
        if (mainMenuScreenBuffer == null) {
            showNewLayerClearBg(new MainMenuScreen());
        } else {
            showNewLayerClearBg(mainMenuScreenBuffer);
            HudManager.remove(mainMenuScreenBuffer.queuingForMatchBar);
        }
    }
}
