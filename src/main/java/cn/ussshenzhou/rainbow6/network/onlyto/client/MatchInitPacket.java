package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

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

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler();
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {
        ClientMatch.init(map, playerUuids);
    }

    public void serverHandler() {

    }
}
