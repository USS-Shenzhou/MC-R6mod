package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 * <p>All in-match packets are only responsible for delivering data.</p>
 * Data processing should be handled by sender's server-match.
 */
@NetPacket
public class ChooseAttackerSpawnPacket {
    public final int spawnPosIndex;

    public ChooseAttackerSpawnPacket(int spawnPosIndex) {
        this.spawnPosIndex = spawnPosIndex;
    }

    @Decoder
    public ChooseAttackerSpawnPacket(FriendlyByteBuf buf) {
        this.spawnPosIndex = buf.readInt();
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(spawnPosIndex);
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
