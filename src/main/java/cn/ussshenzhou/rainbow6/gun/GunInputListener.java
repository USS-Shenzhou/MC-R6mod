package cn.ussshenzhou.rainbow6.gun;

import cn.ussshenzhou.rainbow6.client.input.ModKeyMappingRegistry;
import cn.ussshenzhou.rainbow6.gun.GunFireEvent;
import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
public class GunInputListener {

    private static float partialTickLeftToNextShot;

    public static boolean isInWorld() {
        var minecraft = Minecraft.getInstance();
        return minecraft.player != null && minecraft.screen != null && minecraft.mouseHandler.isMouseGrabbed() && minecraft.isWindowActive();
    }

    public static void handle(InputEvent.MouseButton.Pre event) {
        if (!isInWorld()) {
            return;
        }
        if (event.getAction() == GLFW.GLFW_PRESS) {
            var minecraft = Minecraft.getInstance();
            //noinspection DataFlowIssue
            var item = minecraft.player.getMainHandItem();
            if (item.getItem() instanceof TestGun) {
                var button = event.getButton();
                if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT || button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    event.setCanceled(true);
                }
                if (ModKeyMappingRegistry.FIRE.isDown()) {
                    fire(minecraft.player, item);
                }
            }
        }
    }

    private static void fire(Player shooter, ItemStack gunStack) {
        if (partialTickLeftToNextShot <= 0) {
            //stop running
            shooter.setSprinting(false);
            MinecraftForge.EVENT_BUS.post(new GunFireEvent.Pre(shooter, gunStack));
            var property = ((TestGun) gunStack.getItem()).getProperty();
            partialTickLeftToNextShot += property.getPartialTickBetweenShots();
            //TODO not done
            MinecraftForge.EVENT_BUS.post(new GunFireEvent.Post(shooter, gunStack));
        }
    }


}
