package cn.ussshenzhou.rainbow6.client.input;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappingRegistry {
    @SubscribeEvent
    public static void keyRegistry(RegisterKeyMappingsEvent event) {
        event.register(KeyInputListener.MAIN_MENU);
    }
}
