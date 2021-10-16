package com.ussshenzhou.rainbow6.matchmaking;

import com.ussshenzhou.rainbow6.config.R6ServerMapConfig;
import com.ussshenzhou.rainbow6.config.R6ServerMapConfigManager;
import com.ussshenzhou.rainbow6.entities.DroneEntity;
import com.ussshenzhou.rainbow6.network.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class ServerMatch {
    private UUID uuid;
    private MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
    private R6ServerMapConfig config;
    private int round;
    private String mapName;
    private ArrayList<ServerPlayerEntity> blueTeam = new ArrayList<>();
    private ArrayList<ServerPlayerEntity> orangeTeam = new ArrayList<>();
    private HashMap<ServerPlayerEntity, String> operator = new HashMap<>();
    private int blueTeamScore;
    private int orangeTeamScore;
    public final int MATCH_MADE_PREPARE_TIME = 5000;
    private long startTime;
    private Boolean isBlueAttacker;
    private HashMap<ServerPlayerEntity, PlayerBeforeMatch> beforeMatchPlayers = new HashMap<>();
    private Boolean isReadyStart = false;
    private ArrayList<DroneEntity> drones = new ArrayList<>();
    private BlockPos zonePosMin;
    private BlockPos zonePosMax;
    private AxisAlignedBB gameRange;

    public ServerMatch(UUID uuid, ArrayList<ServerPlayerEntity> blueTeam, ArrayList<ServerPlayerEntity> orangeTeam, Boolean isBlueAttacker) {
        this.uuid = uuid;
        this.isBlueAttacker = isBlueAttacker;
        this.blueTeam = blueTeam;
        this.orangeTeam = orangeTeam;
        for (ServerPlayerEntity serverPlayerEntity : blueTeam) {
            operator.put(serverPlayerEntity, "unknown");
        }
        for (ServerPlayerEntity serverPlayerEntity : orangeTeam) {
            operator.put(serverPlayerEntity, "unknown");
        }
    }

    public void startMatch() {
        //Randomly choose a map.
        mapName = InGameServerProperties.getValidMaps().get(
                (int) (Math.random() * InGameServerProperties.getValidMaps().size()) - 1
        );
        InGameServerProperties.removeValidMaps(mapName);
        config = R6ServerMapConfigManager.getR6Config(mapName);
        zonePosMin = config.getZonePointMin();
        zonePosMax = config.getZonePointMax();
        gameRange =  new AxisAlignedBB(zonePosMin,zonePosMax);
        HashMap<String, BlockPos> spawnPos = config.getSpawnPos();
        for (ServerPlayerEntity serverPlayerEntity : operator.keySet()) {
            //send mapName/ZonePos/ScenePos/SceneDir to all players.
            MapScenePackSend.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayerEntity),
                    new MapScenePack(this.mapName,
                            config.getZonePointMin(),
                            config.getZonePointMax(),
                            config.getScenePos().x,
                            config.getScenePos().y,
                            config.getScenePos().z,
                            config.getSceneDir().x,
                            config.getSceneDir().y
                    )
            );
            //send spawn point
            for (String s : spawnPos.keySet()) {
                SpawnPointPackSend.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayerEntity),
                        new SpawnPointPack(s,
                                spawnPos.get(s))
                );
            }
        }
        //tell clients other players and their team.
        ArrayList<UUID> uuidBlue = new ArrayList<>();
        ArrayList<UUID> uuidOrange = new ArrayList<>();
        for (ServerPlayerEntity s : blueTeam) {
            uuidBlue.add(s.getUniqueID());
        }
        for (ServerPlayerEntity s : orangeTeam) {
            uuidOrange.add(s.getUniqueID());
        }
        for (ServerPlayerEntity serverPlayerEntity : operator.keySet()) {
            MatchPlayersPackSend.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayerEntity),
                    new MatchPlayersPack("blue",
                            uuidBlue.toArray(new UUID[0])
                    )
            );
            MatchPlayersPackSend.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayerEntity),
                    new MatchPlayersPack("orange",
                            uuidOrange.toArray(new UUID[0])
                    )
            );
        }
        this.round = 1;
        isReadyStart = true;
        this.startTime = Util.milliTime();
    }

    public void tick() {
        for (ServerPlayerEntity p : operator.keySet()) {
            p.getFoodStats().setFoodLevel(16);
        }
        //start round after match-made-bar(blue bar).
        if (isReadyStart && startTime + MATCH_MADE_PREPARE_TIME >= Util.milliTime()) {
            isReadyStart = false;
            startRound();
        }
    }

    private void initPlayer() {
        for (ServerPlayerEntity p : operator.keySet()) {
            beforeMatchPlayers.put(p, new PlayerBeforeMatch(
                    p.inventory,
                    p.getPositionVec(),
                    p.interactionManager.getGameType(),
                    p.getHealth(),
                    p.getFoodStats()
            ));
            p.inventory.clear();
            p.setGameType(GameType.SURVIVAL);
            p.clearActivePotions();
            p.setHealth(p.getMaxHealth());
            p.getFoodStats().setFoodLevel(16);
        }
    }

    private void startRound() {
        initPlayer();
        newRound();
    }

    private void newRound() {
        //switch Rotation x and y
        for (ServerPlayerEntity p : operator.keySet()) {
            p.setInvisible(true);
            p.setNoGravity(true);
            p.setGameType(GameType.SPECTATOR);
            p.setPositionAndRotation(-225.67273647685965, 31.0, 127.46611772013287, 44.14949f, 12.546349f);
        }
    }

    private void rollbackPlayer() {
        for (ServerPlayerEntity p : operator.keySet()) {
            PlayerBeforeMatch playerBeforeMatch = beforeMatchPlayers.get(p);
            p.inventory.copyInventory(playerBeforeMatch.inventory);
            p.setPositionAndUpdate(playerBeforeMatch.position.x, playerBeforeMatch.position.y, playerBeforeMatch.position.z);
            p.setGameType(playerBeforeMatch.gameMode);
            p.setHealth(playerBeforeMatch.health);
            p.getFoodStats().setFoodLevel(playerBeforeMatch.foodStats.getFoodLevel());
        }
    }

    private class PlayerBeforeMatch {
        private PlayerInventory inventory;
        private Vector3d position;
        private GameType gameMode;
        private float health;
        private FoodStats foodStats;

        public PlayerBeforeMatch(PlayerInventory inventory, Vector3d position, GameType gameMode, float health, FoodStats foodStats) {
            this.inventory = inventory;
            this.position = position;
            this.gameMode = gameMode;
            this.health = health;
            this.foodStats = foodStats;
        }
    }

    public void setPlayerOperator(ServerPlayerEntity serverPlayerEntity, String op) {
        this.operator.merge(serverPlayerEntity, op, (oldO, newO) -> newO);
        //notify to other players
        Set<ServerPlayerEntity> otherPlayers = operator.keySet();
        otherPlayers.remove(serverPlayerEntity);
        for (ServerPlayerEntity s : otherPlayers) {
            PlayerOperatorPackSend.channel.send(PacketDistributor.PLAYER.with(() -> s),
                    new PlayerOperatorPack(s.getUniqueID(), op)
            );
        }
    }

    public ArrayList<DroneEntity> getDrones() {
        return drones;
    }

    public void setDrones(ArrayList<DroneEntity> drones) {
        this.drones = drones;
    }

    public void addDrones(DroneEntity droneEntity) {
        this.drones.add(droneEntity);
    }

    public void removeDrones(DroneEntity droneEntity) {
        this.drones.remove(droneEntity);
    }

    public Boolean getBlueAttacker() {
        return isBlueAttacker;
    }

    public void setBlueAttacker(Boolean blueAttacker) {
        isBlueAttacker = blueAttacker;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public ArrayList<ServerPlayerEntity> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(ArrayList<ServerPlayerEntity> blueTeam) {
        this.blueTeam = blueTeam;
    }

    public ArrayList<ServerPlayerEntity> getOrangeTeam() {
        return orangeTeam;
    }

    public void setOrangeTeam(ArrayList<ServerPlayerEntity> orangeTeam) {
        this.orangeTeam = orangeTeam;
    }

    public HashMap<ServerPlayerEntity, String> getOperator() {
        return operator;
    }

    public void setOperator(HashMap<ServerPlayerEntity, String> operator) {
        this.operator = operator;
    }

    public int getBlueTeamScore() {
        return blueTeamScore;
    }

    public void setBlueTeamScore(int blueTeamScore) {
        this.blueTeamScore = blueTeamScore;
    }

    public int getOrangeTeamScore() {
        return orangeTeamScore;
    }

    public void setOrangeTeamScore(int orangeTeamScore) {
        this.orangeTeamScore = orangeTeamScore;
    }
}
