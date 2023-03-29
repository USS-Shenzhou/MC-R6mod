package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
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
public class RoundPreTopViewPacket {
    final double x, y, z;
    final boolean turn;

    public RoundPreTopViewPacket(double x, double y, double z, boolean turn) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.turn = turn;
    }

    @Decoder
    public RoundPreTopViewPacket(FriendlyByteBuf buf) {
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
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler(context);
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {

    }

    public void serverHandler(Supplier<NetworkEvent.Context> context) {
        if (R6Constants.TEST){
            ServerPlayer player = context.get().getSender();
            player.connection.teleport(x, y, z, turn ? -90 : 180, 90);
            return;
        }
        try {
            ServerPlayer player = context.get().getSender();
            Map map = ServerMatchManager.getPlayerMatch(player).getMap();
            if (posCheck(player, map)) {
                MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
                ServerLevel serverLevel = minecraftServer.getLevel(map.getDimension());
                if (serverLevel == null) {
                    LogUtils.getLogger().error("Failed to find world {}.", map.getDimension().location());
                    //TODO cancel match
                    return;
                }
                if (player.getLevel() == serverLevel) {
                    player.connection.teleport(x, y, z, turn ? -90 : 180, 90);
                } else {
                    //TODO need test
                    player.teleportTo(serverLevel, x, y, z, turn ? -90 : 180, 90);
                }
                player.setGameMode(GameType.SPECTATOR);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private boolean posCheck(ServerPlayer player, Map map) {
        if (map == null) {
            LogUtils.getLogger().warn("Player {} sent a RoundPrepareTopView packet, but it looks like this player is not in a match.",
                    player.getName().getContents());
            return false;
        }
        BlockPos p1 = map.getZonePointMin();
        BlockPos p2 = map.getZonePointMax();
        int dX = (p2.getX() - p1.getX()) * 4;
        int dZ = (p2.getZ() - p1.getZ()) * 4;
        if (x < p1.getX() - dX || x > p2.getX() + dX
                || y < 0 || y > 320
                || z < p1.getZ() - dZ || z > p2.getZ() + dZ
        ) {
            LogUtils.getLogger().warn("Player {} sent a RoundPrepareTopView packet and wants to teleport to ({}, {}, {}),"
                            + " but it looks like this pos is not in the range of designated map.",
                    player.getName().getContents(), x, y, z
            );
            return false;
        }
        return true;
    }

}
