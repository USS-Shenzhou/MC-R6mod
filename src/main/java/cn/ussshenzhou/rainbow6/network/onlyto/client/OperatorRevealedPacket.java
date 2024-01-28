package cn.ussshenzhou.rainbow6.network.onlyto.client;


import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.Operator;
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
public class OperatorRevealedPacket {
    public final UUID revealedPlayer;
    public final Operator operator;

    public OperatorRevealedPacket(UUID revealedPlayer, Operator operator) {
        this.revealedPlayer = revealedPlayer;
        this.operator = operator;
    }

    @Decoder
    public OperatorRevealedPacket(FriendlyByteBuf buf) {
        revealedPlayer = buf.readUUID();
        operator = buf.readEnum(Operator.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(revealedPlayer);
        buf.writeEnum(operator);
    }


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientMatch.operatorRevealed(revealedPlayer, operator);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
