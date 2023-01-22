package cn.ussshenzhou.rainbow6.client.input;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModKeyMappingRegistry {
    @SubscribeEvent
    public static void keyRegistry(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            ClientRegistry.registerKeyBinding(KeyInputListener.MAIN_MENU);
        });
    }
}
