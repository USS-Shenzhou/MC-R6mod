package cn.ussshenzhou.rainbow6.server;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

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
        }
    }

    @SubscribeEvent
    public static void playerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        MatchMaker.getWaitingPlayers().remove((ServerPlayer) event.getEntity());
        //TODO player during match
    }

    @SubscribeEvent
    public static void playerInjured(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            //assist
            ServerMatchManager.receiveLivingEvent(event);
        }
    }

    @SubscribeEvent
    public static void playerDown(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            //down or die
            ServerMatchManager.receiveLivingEvent(event);
        }
    }
}
