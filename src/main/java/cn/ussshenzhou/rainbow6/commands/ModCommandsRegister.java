package cn.ussshenzhou.rainbow6.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber
public class ModCommandsRegister {
    @SubscribeEvent
    public static void onRegR6MSCommand(RegisterCommandsEvent event){
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        R6IDCommand.register(dispatcher);
        R6MapCommand.register(dispatcher);
    }
}
