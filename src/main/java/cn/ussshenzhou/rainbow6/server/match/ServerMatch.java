package cn.ussshenzhou.rainbow6.server.match;

import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class ServerMatch {
    private final UUID id = UUID.randomUUID();
    private final LinkedHashSet<ServerPlayer> teamOrange = new LinkedHashSet<>();
    private final LinkedHashSet<ServerPlayer> teamBlue = new LinkedHashSet<>();

    public ServerMatch(Collection<ServerPlayer> players) {
        //TODO maybe random?
        teamOrange.addAll(players.stream().toList().subList(0,5));
        teamBlue.addAll(players.stream().toList().subList(5,10));
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return id.equals(obj);
    }
}
