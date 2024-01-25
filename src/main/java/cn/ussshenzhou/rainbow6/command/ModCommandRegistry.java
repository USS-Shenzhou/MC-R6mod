package cn.ussshenzhou.rainbow6.command;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

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
