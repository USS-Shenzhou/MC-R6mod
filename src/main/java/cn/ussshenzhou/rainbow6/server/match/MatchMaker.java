package cn.ussshenzhou.rainbow6.server.match;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedHashSet;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MatchMaker {
    private static final LinkedHashSet<ServerPlayer> WAITING_PLAYERS = new LinkedHashSet<>();

    public static void addWaiting(ServerPlayer player) {
        WAITING_PLAYERS.add(player);
    }

    @SubscribeEvent
    public static void removeFromWaiting(PlayerEvent.PlayerLoggedOutEvent event) {
        WAITING_PLAYERS.remove(event.getPlayer());
    }

    public static void kickFromWaiting(ServerPlayer player){
        WAITING_PLAYERS.remove(player);
        //TODO
    }

    public static void tick(){

    }

}
