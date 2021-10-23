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

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class R6DroneControllerPack {
    private UUID uuid;
    private String worldLocation;
    private int id;

    public R6DroneControllerPack(PacketBuffer buffer){
        uuid = buffer.readUniqueId();
        worldLocation = buffer.readString(32767);
        id = buffer.readInt();
    }

    public R6DroneControllerPack(UUID uuid, String worldLocation, int id) {
        this.uuid = uuid;
        this.worldLocation = worldLocation;
        this.id = id;
    }

    public void toBytes(PacketBuffer buffer){
        buffer.writeUniqueId(uuid);
        buffer.writeString(worldLocation);
        buffer.writeInt(id);
    }
    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
                World world = server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(this.worldLocation)));
                Entity entity = world.getEntityByID(id);
                if (entity instanceof DroneEntity) {
                    ((DroneEntity) entity).setController(uuid);
                }
            }
            //from server to client
            else {
            }
        });
        context.get().setPacketHandled(true);
    }
}
