package cn.ussshenzhou.rainbow6.server.match;

import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author USS_Shenzhou
 */
public class ServerMatchManager {
    private static final LinkedHashSet<ServerMatch> MATCHES = new LinkedHashSet<>();

    public static void newMatch(Collection<ServerPlayer> players){
        MATCHES.add(new ServerMatch(players));
    }
}
