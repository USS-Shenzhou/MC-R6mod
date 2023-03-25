package cn.ussshenzhou.rainbow6.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappingRegistry {
    public static final KeyMapping MAIN_MENU = new KeyMapping(
            "key.r6ms.main_menu", KeyConflictContext.IN_GAME, KeyModifier.ALT,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_6, "key.r6ms.category"
    );
    //needcheck crawl or something else?
    public static final KeyMapping CRAWL = new KeyMapping(
            "key.r6ms.crawl", KeyConflictContext.IN_GAME, KeyModifier.NONE,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.r6ms.category"
    );


    @SubscribeEvent
    public static void keyRegistry(RegisterKeyMappingsEvent event) {
        event.register(MAIN_MENU);
        event.register(CRAWL);
    }
}
