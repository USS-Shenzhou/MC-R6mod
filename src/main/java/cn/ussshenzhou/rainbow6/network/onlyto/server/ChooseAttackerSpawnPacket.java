package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(
                () -> {
                    if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                        ServerMatchManager.receiveNetPacket(this, context.get());
                    }
                }
        );
        context.get().setPacketHandled(true);
    }

}
