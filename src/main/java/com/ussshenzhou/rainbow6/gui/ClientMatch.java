package com.ussshenzhou.rainbow6.gui;

import com.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;

import java.util.HashMap;


/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class ClientMatch {
    public static final int MATCH_MADE_PREPARE_TIME = 5000;
    public static final int SCENE_TIME = 7000;
    public static int GUI_TIME = 30 * 1000;
    public static int ROUND_TIME = 3 * 60 * 1000 + 30 * 1000 + 7000;
    private static Minecraft minecraft = Minecraft.getInstance();
    private static String team = "orange";
    private static String operator = "player";
    private static long matchMadeTime;
    private static long roundTime;
    private static Boolean isMatchMade = false;
    private static Boolean isShowIconList = false;
    private static HashMap<NetworkPlayerInfo, String> teamBlue = new HashMap<>();
    private static HashMap<NetworkPlayerInfo, String> teamOrange = new HashMap<>();
    private static Boolean isBlueAttacker = false;
    private static int blueScore = 0;
    private static int orangeScore = 0;
    private static BlockPos zonePosMin = new BlockPos(-320, 19, 100);
    private static BlockPos zonePosMax = new BlockPos(-220, 36, 201);
    private static Vector3d scenePos;
    private static Vector2f sceneDir;
    private static String mapName = "tmp";
    private static int round = 0;
    private static Boolean readyRound = false;
    private static Boolean isShowScene = false;
    private static HashMap<String, BlockPos> spawnPos = new HashMap<String, BlockPos>() {{
        put("sp1", new BlockPos(-236, 25, 134));
        put("sp2", new BlockPos(-236, 25, 162));
    }};
    private static String chosenSPName;
    private static HashMap<NetworkPlayerInfo, Boolean> spottedEnemy = new HashMap<>();
    private static AxisAlignedBB gameRange;

    public static void startMatch() {
        minecraft.world.playSound(minecraft.player.getPosX(), minecraft.player.getPosY(), minecraft.player.getPosZ(), ModSounds.MATCHMADE, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        round = 1;
        operator = "unknown";
        isMatchMade = true;
        matchMadeTime = Util.milliTime();
        roundTime = Util.milliTime() + MATCH_MADE_PREPARE_TIME;
        //isShowIconList = true;
        readyRound = true;
    }

    private static void newRound() {
        //Server will prepare player's position and rotate for render map scene.
        //prepare for render map scene
        //un-grab Mouse
        minecraft.mouseHelper.ungrabMouse();
        //stop render hot-bar and cross-hair
        ForgeIngameGui.renderHotbar = false;
        ForgeIngameGui.renderCrosshairs = false;
        //Render map scene at first.
        isShowScene = true;
        isShowIconList = false;
    }

    public static void tick() {
        //Round start.
        //Stop render match-made-bar(blue bar).
        if (readyRound && roundTime >= Util.milliTime()) {
            readyRound = false;
            newRound();
            isMatchMade = false;
        }
        //Map scene over.
        //Start render prepareGui and playerIconList.
        if (isShowScene && roundTime + SCENE_TIME >= Util.milliTime()) {
            isShowScene = false;
            isShowIconList = true;
            minecraft.displayGuiScreen(new R6RoundPrepareScreen(new TranslationTextComponent("rainbow6.gui.prepare")));
        }
    }

    public static Boolean isInGame() {
        return !"player".equals(operator);
    }

    public static Boolean getIsSpotted(NetworkPlayerInfo networkPlayerInfo) {
        return spottedEnemy.getOrDefault(networkPlayerInfo, false);
    }

    public static void setSpottedEnemy(NetworkPlayerInfo networkPlayerInfo) {
        spottedEnemy.merge(networkPlayerInfo, true, (aBoolean, aBoolean2) -> true);
    }

    public static AxisAlignedBB getGameRange() {
        return gameRange;
    }

    public static void setGameRange(AxisAlignedBB aabb) {
        gameRange = aabb;
    }

    public static HashMap<NetworkPlayerInfo, Boolean> getSpotEnemy() {
        return spottedEnemy;
    }

    public static String getChosenSPName() {
        return chosenSPName;
    }

    public static void setChosenSPName(String chosenSPName) {
        ClientMatch.chosenSPName = chosenSPName;
    }

    public static HashMap<String, BlockPos> getSpawnPos() {
        return spawnPos;
    }

    public static void addSpawnPos(HashMap<String, BlockPos> Pos) {
        spawnPos = Pos;
    }

    public static void addSpawnPos(String name, BlockPos pos) {
        spawnPos.merge(name, pos, (oldP, newP) -> newP);
    }

    public static void setZonePosMax(BlockPos zonePosMax) {
        ClientMatch.zonePosMax = zonePosMax;
    }

    public static Boolean getIsShowScene() {
        return isShowScene;
    }

    public static long getRoundTime() {
        return roundTime;
    }

    public static int getRound() {
        return round;
    }

    public static String getMapName() {
        return mapName;
    }

    public static void setMapName(String mapName) {
        ClientMatch.mapName = mapName;
    }

    public static void setZonePosMin(BlockPos zonePosMin) {
        ClientMatch.zonePosMin = zonePosMin;
    }

    public static BlockPos getZonePosMin() {
        return zonePosMin;
    }

    public static BlockPos getZonePosMax() {
        return zonePosMax;
    }

    public static void setScenePos(Vector3d scenePos) {
        ClientMatch.scenePos = scenePos;
    }

    public static void setSceneDir(Vector2f sceneDir) {
        ClientMatch.sceneDir = sceneDir;
    }

    public static HashMap<NetworkPlayerInfo, String> getTeamBluePlayers() {
        return teamBlue;
    }

    public static HashMap<NetworkPlayerInfo, String> getTeamOrangePLayers() {
        return teamOrange;
    }

    public static void setTeam(String setTeam) {
        team = setTeam;
    }

    public static String getTeam() {
        return team;
    }

    public static Boolean getIsMatchMade() {
        return isMatchMade;
    }

    public static long getMatchMadeTime() {
        return matchMadeTime;
    }

    public static void setTeamPlayerOperator(NetworkPlayerInfo networkPlayerInfo, String operator) {
        if (teamBlue.containsKey(networkPlayerInfo)) {
            teamBlue.replace(networkPlayerInfo, operator);
        } else if (teamOrange.containsKey(networkPlayerInfo)) {
            teamOrange.replace(networkPlayerInfo, operator);
        }
    }

    public static void setTeamPlayer(NetworkPlayerInfo networkPlayerInfo, String s) {
        switch (s) {
            //team
            case "blue":
                setTeamBluePlayer(networkPlayerInfo, "unknown");
                if ("orange".equals(team)) {
                    spottedEnemy.put(networkPlayerInfo, false);
                }
                break;
            case "orange":
                setTeamOrangePlayer(networkPlayerInfo, "unknown");
                if ("blue".equals(team)) {
                    spottedEnemy.put(networkPlayerInfo, false);
                }
                break;
            //operator
            default:
                if (teamBlue.containsValue(s)) {
                    setTeamBluePlayer(networkPlayerInfo, s);
                } else if (teamOrange.containsValue(s)) {
                    setTeamOrangePlayer(networkPlayerInfo, s);
                } else {
                    throw new IllegalArgumentException();
                }
                break;
        }
    }

    public static void setTeamBluePlayer(NetworkPlayerInfo networkPlayerInfo, String operator) {
        teamBlue.merge(networkPlayerInfo, operator, (oldOp, newOp) -> newOp);
    }

    public static void setTeamOrangePlayer(NetworkPlayerInfo networkPlayerInfo, String operator) {
        teamOrange.merge(networkPlayerInfo, operator, (oldOp, newOp) -> newOp);
    }

    public static Boolean getIsShowIconList() {
        return isShowIconList;
    }

    public static Boolean getIsBlueAttacker() {
        return isBlueAttacker;
    }

    public static void setIsBlueAttacker(Boolean is) {
        isBlueAttacker = is;
    }

    public static String getBlueScoreAsString() {
        return Integer.toString(blueScore);
    }

    public static String getOrangeScoreAsString() {
        return Integer.toString(orangeScore);
    }

    public static String getOperator() {
        return operator;
    }

    public static void setOperator(String op) {
        operator = op;
        if ("player".equals(op)) {
            team = "none";
        } else if ("blue".equals(team)) {
            setTeamBluePlayer(minecraft.player.connection.getPlayerInfo(minecraft.player.getUniqueID()), op);
        } else {
            setTeamOrangePlayer(minecraft.player.connection.getPlayerInfo(minecraft.player.getUniqueID()), op);
        }
    }
}
