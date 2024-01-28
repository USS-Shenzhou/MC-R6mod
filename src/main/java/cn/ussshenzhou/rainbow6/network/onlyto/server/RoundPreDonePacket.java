package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class RoundPreDonePacket {

    public RoundPreDonePacket() {
    }

    @Decoder
    public RoundPreDonePacket(FriendlyByteBuf buf) {
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context){
        ServerMatchManager.receiveNetPacket(this, context);
    }

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {

    }
}
