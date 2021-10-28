package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gui.ClientMatch;
import cn.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class EE1DPack {

    public EE1DPack(PacketBuffer buffer) {

    }

    public EE1DPack() {

    }

    public void toBytes(PacketBuffer buffer) {

    }

    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            //from client to server
            if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {

            }
            //from server to client
            else {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player!=null){
                    ClientPlayerEntity playerEntity = minecraft.player;
                    minecraft.world.playSound(playerEntity,playerEntity.getPosX(),playerEntity.getPosY(),playerEntity.getPosZ(),ModSounds.EE1D_MAIN,SoundCategory.PLAYERS,1.0f,1.0f);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
