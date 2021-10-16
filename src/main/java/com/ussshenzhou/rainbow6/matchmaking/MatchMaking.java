package com.ussshenzhou.rainbow6.matchmaking;

import com.ussshenzhou.rainbow6.network.MatchMakingPack;
import com.ussshenzhou.rainbow6.network.MatchMakingPackSend;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class MatchMaking {

    private static ArrayList<ServerPlayerEntity> playersSeeking = new ArrayList<>();
    private static ArrayList<ServerPlayerEntity> blueTeam = new ArrayList<>();
    private static ArrayList<ServerPlayerEntity> orangeTeam = new ArrayList<>();

    public static void addPlayer(UUID uuid) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        ServerPlayerEntity serverPlayerEntity = server.getPlayerList().getPlayerByUUID(uuid);
        playersSeeking.add(serverPlayerEntity);
    }

    public static void removePlayer(UUID uuid) {
        MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        ServerPlayerEntity serverPlayerEntity = server.getPlayerList().getPlayerByUUID(uuid);
        playersSeeking.remove(serverPlayerEntity);
    }

    public static void checkPlayerList() {
        if (playersSeeking.size() >= 10 && !InGameServerProperties.getValidMaps().isEmpty()) {
            int i = 1;
            String blue;
            String orange;
            Boolean isBlueAttacker = new Random().nextBoolean();
            if (isBlueAttacker) {
                blue = "_attacker";
                orange = "_defender";
            } else {
                blue = "_defender";
                orange = "_attacker";
            }
            for (ServerPlayerEntity p : playersSeeking) {
                if (i <= 5) {
                    blueTeam.add(p);
                    playersSeeking.remove(p);
                    MatchMakingPackSend.channel.send(PacketDistributor.PLAYER.with(() -> p),
                            new MatchMakingPack("blue" + blue, p.getUniqueID())
                    );
                } else {
                    orangeTeam.add(p);
                    playersSeeking.remove(p);
                    MatchMakingPackSend.channel.send(PacketDistributor.PLAYER.with(() -> p),
                            new MatchMakingPack("orange" + orange, p.getUniqueID())
                    );
                }
                UUID uuid = UUID.randomUUID();
                ServerMatch serverMatch = new ServerMatch(uuid, blueTeam, orangeTeam, isBlueAttacker);
                serverMatch.startMatch();
                InGameServerProperties.addServerMatch(uuid, serverMatch);
                i++;
                if (i >= 10) {
                    break;
                }
            }
        }
    }
}
