package cn.ussshenzhou.rainbow6.gun;

import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import cn.ussshenzhou.rainbow6.network.onlyto.server.GunShotFirePacket;
import cn.ussshenzhou.rainbow6.util.ClientUtils;
import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class GunInputListener {

    private static boolean triggerPulled = false;

    //-----cache-----
    private static long lastFire = 0;
    private static float cd = 0;
    private static ItemStack gunItemStack = null;
    private static TestGun gunItem = null;
    private static Modifier modifier = null;

    @SubscribeEvent
    public static void handleMouse(InputEvent.MouseButton.Pre event) {
        if (ClientUtils.isNotInWorld()) {
            return;
        }
        var minecraft = Minecraft.getInstance();
        //noinspection DataFlowIssue
        var item = minecraft.player.getMainHandItem();
        if (item.getItem() instanceof TestGun testGun) {
            if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                if (event.getAction() == GLFW.GLFW_PRESS) {
                    triggerPulled = true;
                    event.setCanceled(true);

                    cd = testGun.getFixedProperty().coolDownMS();
                    gunItemStack = item;
                    gunItem = testGun;
                    modifier = testGun.getModifier(item);
                } else {
                    triggerPulled = false;
                    //reset lastFire to mark a semi trigger.
                    lastFire = 0;
                }
            }
            //TODO RIGHT - aim
        }
    }

    @SubscribeEvent
    public static void clearCache(PlayerEvent.PlayerLoggedOutEvent event) {
        triggerPulled = false;
        lastFire = 0;
        cd = 0;
        gunItemStack = null;
        gunItem = null;
        modifier = null;
    }

    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        long current = Util.getMillis();
        if (triggerPulled && current - lastFire >= cd && checkFire()) {
            fire();
            lastFire = current;
        }
    }

    private static boolean checkFire() {
        //check semi
        if (gunItem.getFixedProperty().trigger() == Trigger.SEMI) {
            if (lastFire != 0) {
                return false;
            }
        }
        //TODO check mag

        return true;
    }

    /**
     * Once a shot fire:
     * <p>- Animation and Sound will be handled at CLIENT.</p>
     * <p>- Recoil will be handled at CLIENT, then send to SERVER to check and sync.</p>
     * <p>- Server will be notified to make Projectiles.</p>
     */
    private static void fire() {
        var shooter = Minecraft.getInstance().player;
        //stop running
        //noinspection DataFlowIssue
        shooter.setSprinting(false);

        MinecraftForge.EVENT_BUS.post(new GunShotFireEvent.Pre(shooter));
        NetworkHelper.sendToServer(new GunShotFirePacket());
        MinecraftForge.EVENT_BUS.post(new GunShotFireEvent.Post(shooter));
    }

}
