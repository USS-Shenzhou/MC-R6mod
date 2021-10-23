package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gui.ClientMatch;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class SpawnPointPack {
    private String name;
    private BlockPos pos;

    public SpawnPointPack(PacketBuffer buffer) {
        this.name = buffer.readString(32767);
        this.pos = buffer.readBlockPos();
    }

    public SpawnPointPack(String name, BlockPos pos) {
        this.name = name;
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buffer){
        buffer.writeString(name);
        buffer.writeBlockPos(pos);
    }

    public void handler(Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {

            }
            //from server to client
            else {
                ClientMatch.addSpawnPos(this.name,this.pos);
            }
        });
        context.get().setPacketHandled(true);
    }
}
