package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class ChooseOperatorPacket {
    public final Operator operator;

    public ChooseOperatorPacket(Operator operator) {
        this.operator = operator;
    }

    @Decoder
    public ChooseOperatorPacket(FriendlyByteBuf buf){
        operator = buf.readEnum(Operator.class);
    }

    @Encoder
    public void write(FriendlyByteBuf buf){
        buf.writeEnum(operator);
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            ServerMatchManager.receiveNetPacket(this, context.get());
        }
    }
}
