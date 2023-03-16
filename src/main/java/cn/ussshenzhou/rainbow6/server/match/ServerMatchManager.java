package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.data.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * @author USS_Shenzhou
 */
public class ServerMatchManager {
    private static final LinkedHashSet<ServerMatch> MATCHES = new LinkedHashSet<>();
    private static final HashMap<ServerPlayer, ServerMatch> PLAYERS_IN_MATCH = new HashMap<>();

    public static void newMatch(Collection<ServerPlayer> players) {
        //---dev---
        ServerMatch match = new ServerMatch(players, new Map());
        MATCHES.add(match);
        players.forEach(serverPlayer -> PLAYERS_IN_MATCH.put(serverPlayer, match));
    }

    public static boolean isPlayerInMatch(ServerPlayer player) {
        return PLAYERS_IN_MATCH.containsKey(player);
    }

    public static ServerMatch getPlayerMatch(ServerPlayer player) {
        return PLAYERS_IN_MATCH.get(player);
    }

    public static void tick() {
        MATCHES.forEach(ServerMatch::tick);
    }

    public static <MSG> void receiveNetPacket(MSG packet, NetworkEvent.Context context) {
        PLAYERS_IN_MATCH.get(context.getSender()).receivePacket(packet, context);
    }
}
