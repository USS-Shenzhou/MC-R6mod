package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.network.MatchInitPacket;
import cn.ussshenzhou.rainbow6.server.DelayedTaskManager;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class ServerMatchController {
    private final ServerMatch match;

    public ServerMatchController(ServerMatch match) {
        this.match = match;
    }

    public void startMatch() {
        //send to players
        ArrayList<UUID> uuids = new ArrayList<>();
        match.forEachPlayer(player -> uuids.add(player.getUUID()));
        savePlayerDataAndInit();
        match.sendPacketsToEveryOne(new MatchInitPacket(match.map, uuids));
        match.taskManager.arrange(20, this::newRound);
    }

    private void savePlayerDataAndInit() {
        match.forEachPlayer(player -> match.playerDataBeforeMatch.put(player, player.saveWithoutId(new CompoundTag())));
        //TODO
        //TODO Make other player invisible
    }

    private void newRound() {
        //init roundNumber
        match.currentRoundNumber++;
        //choose a&d
        if (match.currentRoundNumber == 1) {
            match.attackerColor = Math.random() < 0.5 ? TeamColor.ORANGE : TeamColor.BLUE;
        } else if (match.currentRoundNumber % 2 == 1) {
            match.attackerColor = match.attackerColor.opposite();
        }
        //TODO
    }

    private void restorePlayerData() {
        MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        match.forEachPlayer(player -> {
            try {
                restorePlayerDataInternal(player, minecraftServer);
            } catch (NullBeforeMatchDataException ignored) {
                player.load(new CompoundTag());
                player.setGameMode(minecraftServer.getDefaultGameType());

            } catch (WorldNotFoundException ignored) {

            }
            //if any anomaly, clear and teleport to world spawn.
            //TODO

        });
    }

    private void restorePlayerDataInternal(ServerPlayer player, MinecraftServer minecraftServer) throws NullBeforeMatchDataException, WorldNotFoundException {
        CompoundTag tag = match.playerDataBeforeMatch.get(player);
        if (tag == null) {
            LogUtils.getLogger().error("Can't get player {}'s before-match data. This should not happen.", player.getName());
            throw new NullBeforeMatchDataException();
        }
        //restore all
        //in-match inventory will be replaced.
        player.load(tag);
        //gameMode seems not working, need manual restoring.
        player.setGameMode(GameType.byId(tag.getInt("playerGameType")));
        ResourceKey<Level> originalDimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("Dimension")));
        ServerLevel serverLevel = minecraftServer.getLevel(originalDimension);
        if (serverLevel == null) {
            LogUtils.getLogger().warn("Failed to find world {}.", originalDimension.location());
            throw new WorldNotFoundException();
        }
        //pos/rot seems not working, need manual restoring.
        ListTag pos = tag.getList("Pos", 6);
        ListTag rot = tag.getList("Rotation", 5);
        if (player.getLevel() == serverLevel) {
            player.connection.teleport(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2), rot.getFloat(0), rot.getFloat(1));
        } else {
            player.teleportTo(serverLevel, pos.getDouble(0), pos.getDouble(1), pos.getDouble(2), rot.getFloat(0), rot.getFloat(1));
        }
    }

    public static class NullBeforeMatchDataException extends Exception {
    }

    public static class WorldNotFoundException extends Exception {
    }
}
