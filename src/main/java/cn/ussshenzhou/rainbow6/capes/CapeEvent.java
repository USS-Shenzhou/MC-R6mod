package cn.ussshenzhou.rainbow6.capes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CapeEvent {
    private static HashSet<String> rendered = new HashSet<>();

    @SubscribeEvent
    public static void capeEvent(RenderPlayerEvent.Pre event) {
        String name = event.getPlayer().getName().getString();
        R6CapeList.R6Capes r6Cape= R6CapeList.getR6Cape(name);
        if (!rendered.contains(name) && r6Cape != null) {
            R6CapeLayer r6CapeLayer = new R6CapeLayer(event.getRenderer(),r6Cape);
            event.getRenderer().addLayer(r6CapeLayer);
            rendered.add(name);
        }
    }
}
