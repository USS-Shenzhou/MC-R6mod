package cn.ussshenzhou.rainbow6.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommandRegistry {
    @SubscribeEvent
    public static void regCommand(RegisterCommandsEvent event) {
        MapCommand.register(event.getDispatcher());
    }
}
