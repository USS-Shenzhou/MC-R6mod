package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class MatchMakerChangePacket {
    private final Flag flag;

    public MatchMakerChangePacket(Flag flag) {
        this.flag = flag;
    }

    @Decoder
    public MatchMakerChangePacket(FriendlyByteBuf buf) {
        this.flag = buf.readEnum(Flag.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(flag);
    }

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {

    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {
        switch (flag) {
            case JOIN -> MatchMaker.addWaiting((ServerPlayer) context.player().get());
            default -> {
            }
        }
    }


    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum Flag {
        JOIN
    }
}
