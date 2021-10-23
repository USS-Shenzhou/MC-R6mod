package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gui.ClientMatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 * server tells client other players in this match and their team.
 */
public class MatchPlayersPack {
    private String team;
    private UUID[] uuid = new UUID[5];

    public MatchPlayersPack(PacketBuffer packetBuffer) {
        team = packetBuffer.readString(32767);
        for (UUID u : uuid){
            u = packetBuffer.readUniqueId();
        }
    }

    public MatchPlayersPack(String team, UUID[] uuid) {
        this.team = team;
        this.uuid = uuid;
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(team);
        for (UUID u : uuid){
            buffer.writeUniqueId(u);
        }
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {

            }
            //from server to client
            else {
                ClientPlayNetHandler clientPlayNetHandler = Minecraft.getInstance().player.connection;
                for (UUID u :uuid){
                    ClientMatch.setTeamPlayer(clientPlayNetHandler.getPlayerInfo(u),this.team);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
