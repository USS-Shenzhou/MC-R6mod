package cn.ussshenzhou.rainbow6.server;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralListener {

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event){
        if (event.phase== TickEvent.Phase.END){
            MatchMaker.tick();
        }
    }
}
