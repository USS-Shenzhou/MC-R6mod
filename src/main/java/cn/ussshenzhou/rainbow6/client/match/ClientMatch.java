package cn.ussshenzhou.rainbow6.client.match;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.hud.AutoCloseHud;
import cn.ussshenzhou.rainbow6.client.gui.hud.PromptHud;
import cn.ussshenzhou.rainbow6.client.gui.screen.MatchBeginMapSceneScreen;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundBeginMapSceneScreen;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class ClientMatch {
    private static Minecraft minecraft = Minecraft.getInstance();
    private static Map map = new Map();
    private static LinkedHashSet<Player> teamOrange;
    private static LinkedHashSet<Player> teamBlue;
    private static Side side = Side.DEFENDER;
    private static int bombSiteIndex = 0;
    private static int currentRound = 0;
    private static boolean renderPlayer = true;
    //---dev---
    private static boolean isInMatch = true;

    //----------Start a new Match----------

    public static void init(Map m, ArrayList<UUID> u) {
        isInMatch = true;
        map = m;
        teamOrange = new LinkedHashSet<>();
        u.subList(0, 5).forEach(uuid -> teamOrange.add(getPlayerById(uuid)));
        u.subList(5, 10).forEach(uuid -> teamBlue.add(getPlayerById(uuid)));
        if (teamOrange.size() != 5 || teamBlue.size() != 5) {
            //TODO
        }
        notifyGui();
    }

    private static void notifyGui() {
        ScreenManager.mainMenuScreenBuffer.queuingForMatchBar.showGetQueued();
        ScreenManager.mainMenuScreenBuffer.stopQueuing();
        ScreenManager.clearLayers();
        ScreenManager.showNewLayerClearBg(new MatchBeginMapSceneScreen());
        //TODO disable food hud
    }

    //----------Start a new Round----------

    public static void newRound(TeamColor attackerColor) {
        currentRound++;
        side = getTeamColor() == attackerColor ? Side.ATTACKER : Side.DEFENDER;
        ScreenManager.clearLayers();
        ScreenManager.showNewLayerClearBg(new RoundBeginMapSceneScreen());
        //RoundPrepareScreen will be called by RoundBeginMapSceneScreen
        //RoundPrepareScreen.newRoundPrepareScreenAndShow();
    }

    public static void preStage() {
        ScreenManager.clearLayers();
        AutoCloseHud.ifPresentFormerThenRemoveAndAdd(new PromptHud.PrepareStage());
        //TODO other HUD
    }

    public static void actStage() {
        AutoCloseHud.ifPresentFormerThenRemoveAndAdd(new PromptHud.ActStage());
        //TODO other HUD
    }

    //----------Event handler----------

    public static void operatorRevealed(UUID revealed, Operator operator) {
        Player player = getPlayerById(revealed);
        if (isAlly(player)) {
            //allies operator
            if (ScreenManager.getCurrentLayer() instanceof RoundPrepareScreen roundPrepareScreen) {
                roundPrepareScreen.getOperatorsPanel().disableOperator(operator);
            }
            ScreenManager.playerInfoBarHud.getAllies().setPlayerOperator(getIndexOf(getAllies(), player), operator);
        } else {
            //enemies operator
            ScreenManager.playerInfoBarHud.getEnemies().setPlayerOperator(getIndexOf(getEnemies(), player), operator);
        }
    }


    //----------Util----------

    public static Player getPlayerById(UUID playerId) {
        Level level = minecraft.level;
        return level == null ? null : level.getPlayerByUUID(playerId);
    }

    public static TeamColor getTeamColor() {
        return teamOrange.contains(minecraft.player) ? TeamColor.ORANGE : TeamColor.BLUE;
    }

    public static TeamColor getTeamColor(Player player) {
        return teamOrange.contains(player) ? TeamColor.ORANGE : TeamColor.BLUE;
    }

    public static boolean isAlly(Player player) {
        return getTeamColor() == getTeamColor(player);
    }

    private static <T> int getIndexOf(Collection<T> collection, T t) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.stream().toList().get(i).equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public static LinkedHashSet<Player> getAllies() {
        return teamOrange.contains(minecraft.player) ? teamOrange : teamBlue;
    }

    public static LinkedHashSet<Player> getEnemies() {
        return teamOrange.contains(minecraft.player) ? teamBlue : teamOrange;
    }

    public static boolean isInMatch() {
        return isInMatch;
    }

    public static void stopRenderPlayer() {
        renderPlayer = false;
    }

    public static void resumeRenderPlayer() {
        renderPlayer = true;
    }

    public static boolean isRenderPlayer() {
        return renderPlayer;
    }

    public static int getCurrentRoundNumber() {
        return currentRound;
    }

    public static Side getSide() {
        return side;
    }

    public static int getRoundsLeftToExchange() {
        return 2 - currentRound % 2 + 1;
    }

    public static int getAllyScore() {
        //TODO scoreboard
        return 0;
    }

    public static int getEnemyScore() {
        return 0;
        //TODO scoreboard
    }

    public static Map getMap() {
        return map;
    }

    public static int getNumberInTeam() {
        //---dev---
        //return getIndexOf(getAllies(), minecraft.player);
        return 2;
    }

    public static void setBombSiteIndex(int bombSiteIndex) {
        ClientMatch.bombSiteIndex = bombSiteIndex;
    }

    public static int getBombSiteIndex() {
        return bombSiteIndex;
    }

    public static int getAttackerAliveAmount() {
        //TODO scoreboard
        return 1;
    }

    public static int getDefenderAliveAmount() {
        //TODO scoreboard
        return 5;
    }
}
