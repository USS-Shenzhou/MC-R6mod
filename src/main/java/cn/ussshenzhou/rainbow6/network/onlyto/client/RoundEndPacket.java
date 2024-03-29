package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class RoundEndPacket {
    public final TeamColor winner;

    public RoundEndPacket(TeamColor winner) {
        this.winner = winner;
    }

    @Decoder
    public RoundEndPacket(FriendlyByteBuf buf) {
        winner = buf.readEnum(TeamColor.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(winner);
    }


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientMatch.roundEnd(winner);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
