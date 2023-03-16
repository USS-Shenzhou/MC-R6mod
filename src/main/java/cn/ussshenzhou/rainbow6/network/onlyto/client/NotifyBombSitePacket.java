package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class NotifyBombSitePacket {
    int bombSiteIndex;

    public NotifyBombSitePacket(int bombSiteIndex) {
        this.bombSiteIndex = bombSiteIndex;
    }

    @Decoder
    public NotifyBombSitePacket(FriendlyByteBuf buf) {
        bombSiteIndex = buf.readInt();
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(bombSiteIndex);
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
        ClientMatch.setBombSiteIndex(bombSiteIndex);
    }

    public void serverHandler() {

    }
}
