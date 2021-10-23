package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.entities.DroneEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class R6DroneMotionPack {
    private double x;
    private double y;
    private double z;
    private String worldLocation;
    private int id;
    private double yaw;
    private double pitch;

    public R6DroneMotionPack(PacketBuffer buffer) {
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        worldLocation = buffer.readString(32767);
        id = buffer.readInt();
        yaw = buffer.readDouble();
        pitch = buffer.readDouble();
    }

    public R6DroneMotionPack(double x, double y, double z, String worldLocation, int id, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldLocation = worldLocation;
        this.id = id;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeString(worldLocation);
        buffer.writeInt(id);
        buffer.writeDouble(yaw);
        buffer.writeDouble(pitch);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
                World world = server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(this.worldLocation)));
                Entity entity = world.getEntityByID(id);
                if (entity instanceof DroneEntity) {
                    entity.setMotion(x, y, z);
                    ((DroneEntity) entity).setRotation(yaw, pitch);
                }
            }
            //from server to client
            else {

            }
        });
        context.get().setPacketHandled(true);
    }
}
