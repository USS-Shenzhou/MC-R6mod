package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class RoundPrepareTopView {
    final double x, y, z;
    final boolean turn;

    public RoundPrepareTopView(double x, double y, double z, boolean turn) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.turn = turn;
    }

    @Decoder
    public RoundPrepareTopView(FriendlyByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        turn = buf.readBoolean();
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeBoolean(turn);
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(
                () -> {
                    if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
                        serverHandler(context);
                    } else {
                        clientHandler();
                    }
                }
        );
        context.get().setPacketHandled(true);

    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {

    }

    public void serverHandler(Supplier<NetworkEvent.Context> context) {
        try {
            //TODO security check
            //MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
            //ServerLevel serverLevel = minecraftServer.getLevel(Level.OVERWORLD);
            //TODO multi-level support : ServerLevelData#getLevelName
            context.get().getSender().connection.teleport(x, y, z, turn ? -90 : 180, 90);
            context.get().getSender().setGameMode(GameType.SPECTATOR);
        } catch (NullPointerException ignored) {
        }
    }

}
