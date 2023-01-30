package cn.ussshenzhou.rainbow6.server.match;

import net.minecraft.server.level.ServerPlayer;

import java.util.LinkedHashSet;

/**
 * @author USS_Shenzhou
 */
public class MatchMaker {
    private static final LinkedHashSet<ServerPlayer> WAITING_PLAYERS = new LinkedHashSet<>();

    public static void addWaiting(ServerPlayer player) {
        WAITING_PLAYERS.add(player);
    }

    public static void kickFromWaiting(ServerPlayer player){
        WAITING_PLAYERS.remove(player);
        //TODO
    }

    public static void tick(){
        //TODO MMR
        //TODO smarter matchmaker
        if (WAITING_PLAYERS.size() >= 10){
            ServerMatchManager.newMatch(WAITING_PLAYERS.stream().toList().subList(0,10));
        }
    }

    public static LinkedHashSet<ServerPlayer> getWaitingPlayers(){
        return WAITING_PLAYERS;
    }

}
