package cn.ussshenzhou.rainbow6.server;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralServerListener {

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.haveTime()) {
            MatchMaker.tick();
            ServerMatchManager.tick();
            DelayedTaskManager.tick();
        }
    }

    @SubscribeEvent
    public static void playerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        MatchMaker.getWaitingPlayers().remove(event.getPlayer());
        //TODO player during match
    }
}
