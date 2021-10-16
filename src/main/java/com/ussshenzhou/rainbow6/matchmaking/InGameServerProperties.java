package com.ussshenzhou.rainbow6.matchmaking;

import com.google.common.collect.Lists;
import com.ussshenzhou.rainbow6.config.R6ServerMapConfigManager;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class InGameServerProperties {
    private static ArrayList<String> maps = new ArrayList<>();
    private static HashMap<UUID, ServerMatch> serverMatches = new HashMap<>();
    private static ArrayList<String> validMaps = new ArrayList<>();

    public static ArrayList<String> getValidMaps() {
        return validMaps;
    }

    public static void setValidMaps(ArrayList<String> validMaps) {
        InGameServerProperties.validMaps = validMaps;
    }

    public static void refreshValidMaps() {
        refreshMaps();
        //slow ?
        try {
            for (String m : maps) {
                if (R6ServerMapConfigManager.getR6Config(m).isDone()) {
                    validMaps.add(m);
                }
            }
        } catch (NullPointerException ignored) {

        }
    }

    public static void setValidMaps(String map) {
        validMaps.add(map);
    }

    public static void removeValidMaps(String map) {
        validMaps.remove(map);
    }

    public static ArrayList<String> getMaps() {
        return maps;
    }

    public static String[] getMapsArray() {
        return maps.toArray(new String[0]);
    }

    public static void setMaps(ArrayList<String> map) {
        maps = map;
    }

    public static void refreshMaps() {
        maps = R6ServerMapConfigManager.getMapNames();
    }

    public static ArrayList<ServerMatch> getServerMatches() {
        return Lists.newArrayList(serverMatches.values());
    }

    public static ServerMatch getServerMatch(ServerPlayerEntity serverPlayerEntity) {
        for (ServerMatch serverMatch : Lists.newArrayList(serverMatches.values())) {
            if (serverMatch.getOperator().containsKey(serverPlayerEntity)) {
                return serverMatch;
            }
        }
        throw new NullPointerException();
    }

    public static void addServerMatch(UUID uuid, ServerMatch match) {
        serverMatches.merge(uuid, match, ((match1, match2) -> match2));
    }

    public static ServerMatch getServerMatchByUuid(UUID uuid) {
        return serverMatches.getOrDefault(uuid, null);
    }

    public static void removeServerMatch(ServerMatch match) {
        serverMatches.remove(match);
    }
}
