package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.action.Actions;
import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.mixinproxy.FoodDataProxy;
import cn.ussshenzhou.rainbow6.network.onlyto.client.*;
import cn.ussshenzhou.rainbow6.network.onlyto.server.ChooseAttackerSpawnPacket;
import cn.ussshenzhou.rainbow6.network.onlyto.server.ChooseOperatorPacket;
import cn.ussshenzhou.rainbow6.network.onlyto.server.RoundPreDonePacket;
import cn.ussshenzhou.rainbow6.server.DelayedTask;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class ServerMatchController {
    private ServerMatch match;

    public ServerMatchController(ServerMatch match) {
        this.match = match;
    }

    //----------Start a new Match----------

    public void startMatch() {
        //send to players
        ArrayList<UUID> uuids = new ArrayList<>();
        match.forEachPlayer(player -> uuids.add(player.getUUID()));
        match.forEachPlayer(player -> match.playerDataBeforeMatch.put(player, player.saveWithoutId(new CompoundTag())));
        match.sendPacketsToEveryOne(new MatchInitPacket(match.map, uuids));
        //----------Give additional 1 sec to QueuingForMatchBar to notify player----------
        match.taskManager.arrange(20, this::newRound);
        //TODO tp to mapScene
        //TODO map border
    }

    //----------Start a new Round----------
    DelayedTask roundPreTimeout;

    private void newRound() {
        //init roundNumber
        match.currentRoundNumber++;
        //choose a&d
        if (match.currentRoundNumber == 1) {
            match.attackerColor = match.random.nextBoolean() ? TeamColor.ORANGE : TeamColor.BLUE;
        } else if (match.currentRoundNumber % 2 == 1) {
            match.attackerColor = match.attackerColor.opposite();
        }
        //choose bombSite
        match.bombSiteIndex = Mth.randomBetweenInclusive(match.random, 0, match.map.getBombSites().size() - 1);
        match.sendPacketsToDefenders(new NotifyBombSitePacket(match.bombSiteIndex));
        //be ready for map top view
        match.forEachPlayer(player -> player.setGameMode(GameType.SPECTATOR));
        match.sendPacketsToEveryOne(new RoundStartPacket(match.attackerColor));
        //TODO tp to mapScene
        //TODO Operator select handler
        //TODO loadout select handler
        //RoundBeginMapScene: 4s
        //MapTopViewHelper.checkPlayerPosition: 10s
        //MapTopViewHelper.checkChunkRender: 20s
        //checkPlayerPosition - multiple time as Defender: should cost less than 10s
        //preRoundScreen: 40s
        //redundant: 10s
        roundPreTimeout = new DelayedTask(20 * (4 + 10 + 20 + 10 + 40), () -> {
            //TODO timeout
        });
        match.taskManager.addTask(roundPreTimeout);
        match.scoreboard.newRound();
    }

    /**
     * Fired by
     *
     * @see ServerMatchController#roundPreDone(ServerPlayer)
     */
    private void preparationStage() {
        match.scoreboard.preparationStage();
        //TODO give loadout
        CompoundTag blankTag = new CompoundTag();
        match.forEachPlayer(player -> {
            player.setGameMode(GameType.ADVENTURE);
            player.load(blankTag);
            //needtest will this pass to client automatically?
            ((FoodDataProxy) player.getFoodData()).setR6msFoodEnabled(false);
        });
        //----attackers----
        match.getAttackers().forEach(player -> {
            int spawnIndex = match.attackerSpawns.getOrDefault(player, Mth.randomBetweenInclusive(match.random, 0, match.map.getSpawnPositions().size() - 1));
            BlockPos spawnPos = match.map.getSpawnPositions().get(spawnIndex).getSpawnPosPos();
            findAndSpawn(player, spawnPos);
        });
        //----defenders----
        Map.BombSite bombSites = match.map.getBombSites().get(match.bombSiteIndex);
        match.getDefenders().forEach(player -> {
            BlockPos bombPos = match.random.nextBoolean() ? bombSites.getSubSite1Pos() : bombSites.getSubSite2Pos();
            findAndSpawn(player, bombPos);
        });
        //TODO use cellphone

        match.sendPacketsToEveryOne(new PreparationStagePacket());
        //TODO set inside-outside border
        match.taskManager.arrange(45 * 20, this::actStage);
    }

    /**
     * @see ServerPlayer#fudgeSpawnLocation(ServerLevel)
     */
    private void findAndSpawn(ServerPlayer player, BlockPos center) {
        ServerLevel level = player.getLevel();
        int radius = 7;
        int diameter = radius * 2 + 1;
        int square = diameter * diameter;
        int j1 = 17;
        int k1 = match.random.nextInt(square);
        for (int i = 0; i < square; ++i) {
            int i2 = (k1 + j1 * i) % square;
            int j2 = i2 % (radius * 2 + 1);
            int k2 = i2 / (radius * 2 + 1);
            BlockPos randomPos = new BlockPos(center.getX() + j2 - radius, center.getY(), center.getZ() + k2 - radius);
            player.moveTo(randomPos, 0, 0);
            if (level.noCollision(player)) {
                break;
            }
            if (i == square - 1) {
                //can't find a spawn pos
                player.moveTo(center, 0, 0);
            }
        }
    }

    private void actStage() {
        //TODO remove inside-outside border
    }

    private void roundEnd(Side winner) {
        TeamColor winnerColor = winner == Side.ATTACKER ? match.attackerColor : match.attackerColor.opposite();
        match.scoreboard.winnerSides.add(winnerColor);
        //TODO
        afterRound();
    }

    private void afterRound() {
        match.attackerSpawns.clear();
        match.preparedPlayers.clear();
        match.chosenOperators.clear();
        match.downed.clear();
        match.planted = false;
    }

    //----------End Match----------

    private void endMatch() {
        //TODO


        afterMatch();
    }

    private void afterMatch() {
        ServerMatchManager.removeMatch(match);
        match.scoreboard.afterMatch();

        //Break circular references for GC convenience.
        match = null;
    }

    private void playerAfterMatch(ServerPlayer player) {
        ((FoodDataProxy) player.getFoodData()).setR6msFoodEnabled(true);
        restorePlayerData(player);
    }

    private void restorePlayerData(ServerPlayer player) {
        MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        try {
            restorePlayerDataInternal(player, minecraftServer);
        } catch (NullBeforeMatchDataException ignored) {
            player.load(new CompoundTag());
            player.setGameMode(minecraftServer.getDefaultGameType());
            teleportToOverWorldSpawn(minecraftServer, player);
        } catch (WorldNotFoundException ignored) {
            teleportToOverWorldSpawn(minecraftServer, player);
        }
    }

    private void teleportToOverWorldSpawn(MinecraftServer minecraftServer, ServerPlayer player) {
        ServerLevel overWorld = minecraftServer.overworld();
        LevelData data = overWorld.getLevelData();
        player.teleportTo(overWorld, data.getXSpawn(), data.getYSpawn(), data.getZSpawn(), data.getSpawnAngle(), 0);
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
        ResourceKey<Level> originalDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("Dimension")));
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

    //----------Network Handle----------

    public <MSG> void receivePacket(MSG packet, NetworkEvent.Context context) {
        if (packet instanceof ChooseAttackerSpawnPacket chooseAttackerSpawn) {
            attackerChooseSpawn(chooseAttackerSpawn, context);
        } else if (packet instanceof RoundPreDonePacket roundPreDone) {
            roundPreDone(context.getSender());
        } else if (packet instanceof ChooseOperatorPacket chooseOperator) {
            chooseOperator(context.getSender(), chooseOperator.operator);
        }
    }

    private void attackerChooseSpawn(ChooseAttackerSpawnPacket chooseAttackerSpawn, NetworkEvent.Context context) {
        if (!match.getAttackers().contains(context.getSender())) {
            incorrectPacket(chooseAttackerSpawn, context);
            return;
        }
        match.attackerSpawns.put(context.getSender(), chooseAttackerSpawn.spawnPosIndex);
    }

    private void roundPreDone(ServerPlayer player) {
        match.preparedPlayers.add(player);
        if (match.preparedPlayers.size() == 10) {
            match.taskManager.removeTask(roundPreTimeout);
            preparationStage();
        }
    }

    private void chooseOperator(ServerPlayer player, Operator operator) {
        match.chosenOperators.put(player, operator);
        match.sendToFriendly(player, new OperatorRevealedPacket(player.getUUID(), operator));
    }

    private <MSG> void incorrectPacket(MSG packet, NetworkEvent.Context context) {
        incorrectPacket(packet, context, "");
    }

    private <MSG> void incorrectPacket(MSG packet, NetworkEvent.Context context, String message) {
        LogUtils.getLogger().warn("Received unreasonable network packet {} from {}. {}", packet, context.getSender(), message);
    }

    //----------Event Handle----------

    public void receiveEvent(ServerPlayer player, Event event) {
        if (event instanceof LivingHurtEvent hurt) {
            //TODO assist recorder
        } else if (event instanceof LivingDeathEvent death) {
            handleLivingDeath(player, death);
        }
    }

    private void handleLivingDeath(ServerPlayer player, LivingDeathEvent event) {
        if (match.downed.contains(player)) {
            playerDeath(player);
            match.scoreboard.playerKilledBy(player, (ServerPlayer) event.getSource().getEntity());
        } else {
            match.downed.add(player);
            event.setCanceled(true);
            player.setHealth(player.getMaxHealth());
            //TODO bleeding effect
            match.sendPacketsTo(List.of(player), new PlayerDownPacket());
            //TODO heal
            match.scoreboard.playerDownedBy(player, (ServerPlayer) event.getSource().getEntity());
        }
    }

    private void playerDeath(ServerPlayer player) {
        match.sendPacketsToEveryOne(new PlayerDeathPacket());
        setSpectatorByDeath(player);
        checkRoundEndByDeath();
    }

    private void setSpectatorByDeath(ServerPlayer player) {
        //TODO spectator mode
        player.setGameMode(GameType.SPECTATOR);
    }

    private void checkRoundEndByDeath() {
        boolean defenderAllDied = match.getDefenders().stream().noneMatch(player ->
                (!player.isSpectator()) || ActionCapability.get(player).getInstanceOf(Actions.DOWN).isDoing());
        boolean attackerAllDied = match.getAttackers().stream().noneMatch(player ->
                (!player.isSpectator()) || ActionCapability.get(player).getInstanceOf(Actions.DOWN).isDoing());
        //noinspection IfStatementWithIdenticalBranches
        if (!match.planted) {
            if (attackerAllDied) {
                roundEnd(Side.DEFENDER);
                return;
            }
            if (defenderAllDied) {
                roundEnd(Side.ATTACKER);
                return;
            }
        } else {
            if (defenderAllDied) {
                roundEnd(Side.ATTACKER);
                return;
            }
        }
    }
}
