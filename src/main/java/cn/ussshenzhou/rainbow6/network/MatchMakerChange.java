package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.server.match.MatchMaker;
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
public class MatchMakerChange {
    private final Flag flag;

    public MatchMakerChange(Flag flag) {
        this.flag = flag;
    }

    @Decoder
    public MatchMakerChange(FriendlyByteBuf buf) {
        this.flag = buf.readEnum(Flag.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(flag);
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(
                () -> {
                    if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                        serverHandler(context);
                    } else {
                        clientHandler();
                    }
                }
        );
        context.get().setPacketHandled(true);

    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler(){

    }

    public void serverHandler(Supplier<NetworkEvent.Context> context){
        switch (flag){
            case JOIN -> MatchMaker.addWaiting(context.get().getSender());
            default -> {}
        }
    }


    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum Flag {
        JOIN
    }
}
