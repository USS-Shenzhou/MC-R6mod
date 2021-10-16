package com.ussshenzhou.rainbow6.network;

import com.ussshenzhou.rainbow6.gui.ClientMatch;
import com.ussshenzhou.rainbow6.matchmaking.InGameServerProperties;
import com.ussshenzhou.rainbow6.matchmaking.ServerMatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
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
 * sync player-operator between server and client during operator-choosing.
 */
public class PlayerOperatorPack {
    private UUID uuid;
    private String operator;

    public PlayerOperatorPack(PacketBuffer buffer){
        this.uuid = buffer.readUniqueId();
        this.operator = buffer.readString(32767);
    }

    public PlayerOperatorPack(UUID uuid, String operator) {
        this.uuid = uuid;
        this.operator = operator;
    }

    public void toBytes(PacketBuffer buffer){
        buffer.writeUniqueId(uuid);
        buffer.writeString(operator);
    }

    public void handler(Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            //from client to server during preparing stage
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                for (ServerMatch m : InGameServerProperties.getServerMatches()){
                    MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
                    if (m.getOperator().containsKey(server.getPlayerList().getPlayerByUUID(this.uuid))){
                        m.setPlayerOperator(server.getPlayerList().getPlayerByUUID(this.uuid),this.operator);
                        break;
                    }
                }
            }
            //from server to client
            else {
                ClientPlayNetHandler clientPlayNetHandler = Minecraft.getInstance().player.connection;
                ClientMatch.setTeamPlayer(clientPlayNetHandler.getPlayerInfo(this.uuid),this.operator);
            }
        });
        context.get().setPacketHandled(true);
    }
}
