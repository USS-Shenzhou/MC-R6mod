package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.network.annotation.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.util.LogicalSidedProvider;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

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

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {

    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player().get();
        if (R6Constants.TEST) {
            player.connection.teleport(x, y, z, turn ? -90 : 180, 90);
            return;
        }
        try {
            Map map = ServerMatchManager.getPlayerMatch(player).getMap();
            if (posCheck(player, map)) {
                MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
                ServerLevel serverLevel = minecraftServer.getLevel(map.getDimension());
                if (serverLevel == null) {
                    LogUtils.getLogger().error("Failed to find world {}.", map.getDimension().location());
                    //TODO cancel match
                    return;
                }
                if (player.level() == serverLevel) {
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
