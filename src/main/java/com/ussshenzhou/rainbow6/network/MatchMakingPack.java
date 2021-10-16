package com.ussshenzhou.rainbow6.network;

import com.ussshenzhou.rainbow6.gui.ClientMatch;
import com.ussshenzhou.rainbow6.gui.InGameClientProperties;
import com.ussshenzhou.rainbow6.matchmaking.MatchMaking;
import com.ussshenzhou.rainbow6.matchmaking.ServerMatch;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 * server tells client match has been made, team and a/d.
 * client tells server join or exit matchmaking.
 */
public class MatchMakingPack {
    private String message;
    private UUID uuid;

    public MatchMakingPack(PacketBuffer buffer) {
        message = buffer.readString(Short.MAX_VALUE);
        uuid = buffer.readUniqueId();
    }

    public MatchMakingPack(String message, UUID uuid) {
        this.message = message;
        this.uuid = uuid;
    }

    public void toBytes(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.message);
        packetBuffer.writeUniqueId(this.uuid);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                switch (this.message) {
                    case "join":
                        MatchMaking.addPlayer(this.uuid);
                        break;
                    case "exit":
                        MatchMaking.removePlayer(this.uuid);
                        break;
                    default:
                        break;
                }
                //Test
                /*MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
                ServerPlayerEntity playerEntity = server.getPlayerList().getPlayerByUUID(this.uuid);
                playerEntity.inventory.clear();*/
            }
            //from server to client
            else {
                switch (this.message) {
                    case "blue_defender":
                        ClientMatch.setTeam("blue");
                        ClientMatch.setIsBlueAttacker(false);
                        break;
                    case "blue_attacker":
                        ClientMatch.setTeam("blue");
                        ClientMatch.setIsBlueAttacker(true);
                        break;
                    case "orange_defender":
                        ClientMatch.setTeam("orange");
                        ClientMatch.setIsBlueAttacker(true);
                        break;
                    case "orange_attacker":
                        ClientMatch.setTeam("orange");
                        ClientMatch.setIsBlueAttacker(false);
                        break;
                    default:
                        break;
                }
                InGameClientProperties.setIsWaitingMatchMaking(false);
                ClientMatch.startMatch();
            }
        });
        context.get().setPacketHandled(true);
    }
}
