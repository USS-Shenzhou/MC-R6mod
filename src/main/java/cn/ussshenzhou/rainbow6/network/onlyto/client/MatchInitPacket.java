package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class MatchInitPacket {
    Map map;
    ArrayList<UUID> playerUuids;

    public MatchInitPacket(Map map, ArrayList<UUID> playerUuids) {
        this.map = map;
        this.playerUuids = playerUuids;
    }

    @Decoder
    public MatchInitPacket(FriendlyByteBuf buf) {
        map = new Map(buf);
        playerUuids = buf.readCollection(ArrayList::new, FriendlyByteBuf::readUUID);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        map.write(buf);
        buf.writeCollection(playerUuids, FriendlyByteBuf::writeUUID);
    }


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientMatch.init(map, playerUuids);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
