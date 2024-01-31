package cn.ussshenzhou.rainbow6.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappings {

    private static final String CATEGORY = "key.r6ms.category";

    public static final KeyMapping MAIN_MENU = new KeyMapping(
            "key.r6ms.main_menu", KeyConflictContext.IN_GAME, KeyModifier.ALT,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_6, CATEGORY
    );

    //needcheck crawl or something else?
    public static final KeyMapping CRAWL = new KeyMapping(
            "key.r6ms.crawl", KeyConflictContext.IN_GAME, KeyModifier.NONE,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY
    );

    public static final KeyMapping FIRE = new KeyMapping(
            "key.r6ms.fire", KeyConflictContext.IN_GAME, KeyModifier.NONE,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_LEFT, CATEGORY
    );

    public static final KeyMapping AIM = new KeyMapping(
            "key.r6ms.aim", KeyConflictContext.IN_GAME, KeyModifier.NONE,
            InputConstants.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, CATEGORY
    );

    @SubscribeEvent
    public static void keyRegistry(RegisterKeyMappingsEvent event) {
        event.register(MAIN_MENU);
        event.register(FIRE);
        event.register(AIM);
        event.register(CRAWL);
    }
}
