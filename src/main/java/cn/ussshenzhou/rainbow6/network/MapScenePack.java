package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gui.ClientMatch;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class MapScenePack {
    private String mapName;
    private BlockPos zonePosMin;
    private BlockPos zonePosMax;
    private double scenePosX;
    private double scenePosY;
    private double scenePosZ;
    private Float sceneDirX;
    private Float sceneDirY;

    public MapScenePack(String mapName, BlockPos zonePosMin, BlockPos zonePosMax, double scenePosX, double scenePosY, double scenePosZ, Float sceneDirX, Float sceneDirY) {
        this.mapName = mapName;
        this.zonePosMin = zonePosMin;
        this.zonePosMax = zonePosMax;
        this.scenePosX = scenePosX;
        this.scenePosY = scenePosY;
        this.scenePosZ = scenePosZ;
        this.sceneDirX = sceneDirX;
        this.sceneDirY = sceneDirY;
    }

    public MapScenePack(PacketBuffer buffer) {
        this.mapName = buffer.readString(32767);
        this.zonePosMin = buffer.readBlockPos();
        this.zonePosMax = buffer.readBlockPos();
        this.scenePosX = buffer.readDouble();
        this.scenePosY = buffer.readDouble();
        this.scenePosZ = buffer.readDouble();
        this.sceneDirX = buffer.readFloat();
        this.sceneDirY = buffer.readFloat();
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(mapName);
        buffer.writeBlockPos(zonePosMin);
        buffer.writeBlockPos(zonePosMax);
        buffer.writeDouble(scenePosX);
        buffer.writeDouble(scenePosY);
        buffer.writeDouble(scenePosZ);
        buffer.writeFloat(sceneDirX);
        buffer.writeFloat(sceneDirY);
    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {

            }
            //from server to client
            else {
                ClientMatch.setMapName(this.mapName);
                ClientMatch.setZonePosMin(this.zonePosMin);
                ClientMatch.setZonePosMax(this.zonePosMax);
                ClientMatch.setGameRange(new AxisAlignedBB(this.zonePosMin,this.zonePosMax));
                ClientMatch.setScenePos(new Vector3d(this.scenePosX,this.scenePosY,this.scenePosZ));
                ClientMatch.setSceneDir(new Vector2f(this.sceneDirX,this.sceneDirY));
            }
        });
        context.get().setPacketHandled(true);
    }
}
