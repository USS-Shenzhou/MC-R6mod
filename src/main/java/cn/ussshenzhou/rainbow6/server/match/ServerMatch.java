package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.server.DelayedTaskManager;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.network.PacketProxy;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author USS_Shenzhou
 * </p>
 * ServerMatch is resbonsible for storing data, and ServerMatchController is resbonsible for process controlling.
 */
public class ServerMatch {
    final UUID id = UUID.randomUUID();
    final LinkedHashSet<ServerPlayer> teamOrange = new LinkedHashSet<>();
    final LinkedHashSet<ServerPlayer> teamBlue = new LinkedHashSet<>();
    final ServerMatchController controller = new ServerMatchController(this);
    final Map map;
    int currentRoundNumber = 0;
    TeamColor attackerColor;
    int bombSiteIndex = 0;
    final LinkedHashMap<ServerPlayer, CompoundTag> playerDataBeforeMatch = new LinkedHashMap<>();
    DelayedTaskManager taskManager = new DelayedTaskManager();
    final LinkedHashMap<ServerPlayer, Integer> attackerSpawns = new LinkedHashMap<>();
    final LinkedHashSet<ServerPlayer> preparedPlayers = new LinkedHashSet<>();
    RandomSource random = RandomSource.create();

    public ServerMatch(Collection<ServerPlayer> players, Map map) {
        //player team randomization should be in MatchMaker
        teamOrange.addAll(players.stream().toList().subList(0, 5));
        teamBlue.addAll(players.stream().toList().subList(5, 10));
        this.map = map;
        controller.startMatch();
    }

    public void tick() {
        taskManager.tick();
    }

    //----------Network----------

    public <MSG> void sendPacketsTo(Collection<ServerPlayer> players, MSG packet) {
        SimpleChannel channel = PacketProxy.getChannel(packet.getClass());
        if (channel == null) {
            LogUtils.getLogger().error("Failed to find channel for {}.", packet.getClass().getName());
            return;
        }
        players.forEach(player -> channel.send(PacketDistributor.PLAYER.with(() -> player), packet));
    }

    public <MSG> void sendPacketsToEveryOne(MSG packet) {
        forEachPlayer(player -> PacketProxy.getChannel(packet.getClass()).send(PacketDistributor.PLAYER.with(() -> player), packet));
    }

    public <MSG> void sendPacketsToAttackers(MSG packet) {
        getAttackers().forEach(player -> PacketProxy.getChannel(packet.getClass()).send(PacketDistributor.PLAYER.with(() -> player), packet));
    }

    public <MSG> void sendPacketsToDefenders(MSG packet) {
        getDefenders().forEach(player -> PacketProxy.getChannel(packet.getClass()).send(PacketDistributor.PLAYER.with(() -> player), packet));
    }

    public <MSG> void receivePacket(MSG packet, NetworkEvent.Context context) {
        controller.receivePacket(packet, context);
    }

    //--------------------

    public void forEachPlayer(Consumer<ServerPlayer> playerConsumer) {
        teamOrange.forEach(playerConsumer);
        teamBlue.forEach(playerConsumer);
    }

    LinkedHashSet<ServerPlayer> getAttackers() {
        return attackerColor == TeamColor.ORANGE ? teamOrange : teamBlue;
    }

    LinkedHashSet<ServerPlayer> getDefenders() {
        return attackerColor == TeamColor.ORANGE ? teamBlue : teamOrange;
    }

    public UUID getId() {
        return id;
    }

    public Map getMap() {
        return map;
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
