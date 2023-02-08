package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import com.mojang.math.Vector3d;
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
public class RoundPrepareTopView {
    final Vector3d pos;

    public RoundPrepareTopView(Vector3d pos) {
        this.pos = pos;
    }

    @Decoder
    public RoundPrepareTopView(FriendlyByteBuf buf) {
        this.pos = new Vector3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
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
    public void clientHandler() {

    }

    public void serverHandler(Supplier<NetworkEvent.Context> context) {
        try {
            context.get().getSender().teleportTo(pos.x, pos.y, pos.z);
            context.get().getSender().setXRot(-180);
            context.get().getSender().setYRot(90);
        } catch (NullPointerException ignored) {
        }
    }
}
