package cn.ussshenzhou.rainbow6.config;

import cn.ussshenzhou.t88.config.ConfigHelper;
import cn.ussshenzhou.t88.config.MultiInstanceConfigHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigRegister {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ConfigHelper.Universal.loadConfig(new Control());
        });
    }

    @SubscribeEvent
    public static void onServerSetup(FMLDedicatedServerSetupEvent event) {
        event.enqueueWork(() -> {
            MultiInstanceConfigHelper.loadConfigInstances(Map.class, Map.DIR_NAME);
        });
    }
}
