package cn.ussshenzhou.rainbow6.client.input;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputListener {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeyMappings.MAIN_MENU.consumeClick()) {
            ScreenManager.openMainMenuScreen();
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        AnimationPlayerInputListener.tick();
    }
}
