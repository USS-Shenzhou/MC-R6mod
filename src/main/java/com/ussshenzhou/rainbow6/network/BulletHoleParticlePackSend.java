package com.ussshenzhou.rainbow6.network;

import com.ussshenzhou.rainbow6.particles.BulletHoleParticleData;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


/**
 * @author USS_Shenzhou
 */
public class BulletHoleParticlePackSend {

    private final int dir;
    private final BlockPos pos;
    private final double x;
    private final double y;
    private final double z;

    public BulletHoleParticlePackSend(PacketBuffer buffer) {
        dir = buffer.readInt();
        pos = buffer.readBlockPos();
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
    }

    public BulletHoleParticlePackSend(int dir, BlockPos pos,double x,double y,double z) {
        this.dir = dir;
        this.pos = pos;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(dir);
        buf.writeBlockPos(pos);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().world.addParticle(new BulletHoleParticleData(Direction.byIndex(this.dir),this.pos),false,this.x,this.y,this.z,0,0,0);
        });
        ctx.get().setPacketHandled(true);
    }
}
