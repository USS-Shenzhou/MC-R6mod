package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

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

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler(context);
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {

    }

    public void serverHandler(Supplier<NetworkEvent.Context> context) {
        switch (flag) {
            case JOIN -> MatchMaker.addWaiting(context.get().getSender());
            default -> {
            }
        }
    }


    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum Flag {
        JOIN
    }
}
