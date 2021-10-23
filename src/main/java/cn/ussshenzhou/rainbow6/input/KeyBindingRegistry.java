package cn.ussshenzhou.rainbow6.input;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class KeyBindingRegistry {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()-> ClientRegistry.registerKeyBinding(ModInputs.MAIN_MENU_KEY));
    }
}
