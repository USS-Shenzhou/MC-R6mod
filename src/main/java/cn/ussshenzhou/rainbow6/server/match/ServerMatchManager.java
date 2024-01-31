package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.config.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * @author USS_Shenzhou
 */
public class ServerMatchManager {
    private static final LinkedHashSet<ServerMatch> MATCHES = new LinkedHashSet<>();
    private static final HashMap<ServerPlayer, ServerMatch> PLAYERS_IN_MATCH = new HashMap<>();
    private static final LinkedHashSet<ServerMatch> REMOVE = new LinkedHashSet<>();

    public static void newMatch(Collection<ServerPlayer> players) {
        //---dev---
        ServerMatch match = new ServerMatch(players, new Map());
        MATCHES.add(match);
        players.forEach(serverPlayer -> PLAYERS_IN_MATCH.put(serverPlayer, match));
    }

    public static void removeMatch(ServerMatch match) {
        REMOVE.add(match);
        match.forEachPlayer(PLAYERS_IN_MATCH::remove);
    }

    public static boolean isPlayerInMatch(ServerPlayer player) {
        return PLAYERS_IN_MATCH.containsKey(player);
    }

    public static ServerMatch getPlayerMatch(ServerPlayer player) {
        return PLAYERS_IN_MATCH.get(player);
    }

    public static void tick() {
        REMOVE.forEach(MATCHES::remove);
        REMOVE.clear();
        MATCHES.forEach(ServerMatch::tick);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public static <MSG> void receiveNetPacket(MSG packet, PlayPayloadContext context) {
        if (context.player().isPresent()){
            Player player = context.player().get();
            if (PLAYERS_IN_MATCH.containsKey(player)) {
                PLAYERS_IN_MATCH.get(player).receivePacket(packet, context);
            }
        }
    }

    public static void receiveEvent(ServerPlayer player, Event event) {
        if (PLAYERS_IN_MATCH.containsKey(player)) {
            PLAYERS_IN_MATCH.get(player).receiveEvent(player, event);
        }
    }

    public static void receiveLivingEvent(LivingEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && PLAYERS_IN_MATCH.containsKey(player)) {
            PLAYERS_IN_MATCH.get(player).receiveEvent(player, event);
        }
    }
}
