package cn.ussshenzhou.rainbow6.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import cn.ussshenzhou.rainbow6.client.gui.screens.MainMenuScreen;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyInputListener {
    public static final KeyMapping MAIN_MENU = new KeyMapping(
            "key.r6ms.main_menu", KeyConflictContext.IN_GAME, KeyModifier.ALT,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_6, "key.r6ms.category"
    );

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (MAIN_MENU.consumeClick()) {
            ScreenManager.showNewLayerOverBg(new MainMenuScreen());
        }
    }
}
