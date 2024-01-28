package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

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


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientMatch.setBombSiteIndex(bombSiteIndex);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
