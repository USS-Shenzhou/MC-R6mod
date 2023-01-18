package cn.ussshenzhou.rainbow6.input;

import cn.ussshenzhou.rainbow6.gui.InGameClientProperties;
import cn.ussshenzhou.rainbow6.gui.R6MainMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ModInputs {
    public static final KeyBinding MAIN_MENU_KEY = new KeyBinding("key.r6mainmenu",
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_6, "key.category.rainbow6");

    @SubscribeEvent
    public static void mainMenuKey(InputEvent.KeyInputEvent event) {
        if (MAIN_MENU_KEY.isPressed()) {
            //Minecraft.getInstance().displayGuiScreen(new R6RoundPrepareScreen(new TranslationTextComponent("rainbow6.gui.prepare")));
            //Minecraft.getInstance().displayGuiScreen(new R6MainMenuScreen(new TranslationTextComponent("rainbow6.gui.mainmenu")));
        }
    }

    @SubscribeEvent
    public static void closeDroneGui(InputEvent.KeyInputEvent event) {
        //esc
        if (event.getKey() == 256 && InGameClientProperties.isUsingDrone) {
            InGameClientProperties.getR6DroneGui().close();
        }
    }
}
