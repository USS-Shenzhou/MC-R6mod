package cn.ussshenzhou.rainbow6.gun;

import cn.ussshenzhou.rainbow6.client.input.ModKeyMappingRegistry;
import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import cn.ussshenzhou.rainbow6.network.onlyto.server.GunShotFirePacket;
import cn.ussshenzhou.rainbow6.util.ClientUtils;
import cn.ussshenzhou.t88.analyzer.back.T88AnalyzerClient;
import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class GunInputHandler {

    private static float tickLeftToNextShot;

    public static void handle(InputEvent.MouseButton.Pre event) {
        if (ClientUtils.isNotInWorld()) {
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

    /**
     * Once a shot fire:
     * <p>- Animation and Sound will be handled at CLIENT.</p>
     * <p>- Recoil will be handled at CLIENT, then send to SERVER to check and sync.</p>
     * <p>- Server will be notified to make Projectiles.</p>
     */
    private static void fire(Player shooter, ItemStack gunStack) {
        if (tickLeftToNextShot <= 0) {
            //stop running
            shooter.setSprinting(false);
            MinecraftForge.EVENT_BUS.post(new GunShotFireEvent.Pre(shooter, gunStack));
            var property = ((TestGun) gunStack.getItem()).getProperty();
            tickLeftToNextShot += property.getPartialTickBetweenShots();
            NetworkHelper.sendToServer(new GunShotFirePacket());
            MinecraftForge.EVENT_BUS.post(new GunShotFireEvent.Post(shooter, gunStack));
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (ClientUtils.isNotInWorld()) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            tickStart();
        } else {
            tickEnd();
        }
    }

    private static void tickStart() {
        if (tickLeftToNextShot > 0) {
            tickLeftToNextShot -= 1;
        }

    }

    private static void tickEnd() {

    }

}
