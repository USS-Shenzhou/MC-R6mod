package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.server.match.R6ServerScoreboard;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

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


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientMatch.syncPlayerScore(player, score);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
