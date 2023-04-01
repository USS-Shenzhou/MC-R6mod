package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.server.match.R6ServerScoreboard;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class SyncR6ScoreboardPacket {
    UUID player;
    R6ServerScoreboard.PlayerScoresBase score;

    public SyncR6ScoreboardPacket(UUID player, R6ServerScoreboard.PlayerScoresServer score) {
        this.player = player;
        this.score = score;
    }

    @Decoder
    public SyncR6ScoreboardPacket(FriendlyByteBuf buf) {
        player = buf.readUUID();
        score = new R6ServerScoreboard.PlayerScoresBase(buf);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(player);
        score.encode(buf);
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
        ClientMatch.syncPlayerScore(player, score);
    }

    public void serverHandler() {

    }
}
