package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class RoundStartPacket {
    TeamColor attackColor;

    public RoundStartPacket(TeamColor attackColor) {
        this.attackColor = attackColor;
    }

    @Decoder
    public RoundStartPacket(FriendlyByteBuf buf) {
        attackColor = buf.readEnum(TeamColor.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(attackColor);
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(
                () -> {
                    if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                        serverHandler();
                    } else {
                        clientHandler();
                    }
                }
        );
        context.get().setPacketHandled(true);

    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {
        ClientMatch.newRound(attackColor);
    }

    public void serverHandler() {

    }
}
