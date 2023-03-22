package cn.ussshenzhou.rainbow6.network.onlyto.client;


import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.Operator;
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
        ClientMatch.operatorRevealed(revealedPlayer, operator);
    }

    public void serverHandler() {

    }
}
