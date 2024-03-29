package cn.ussshenzhou.rainbow6.client.match;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.hud.AutoCloseHud;
import cn.ussshenzhou.rainbow6.client.gui.hud.PromptHud;
import cn.ussshenzhou.rainbow6.client.gui.screen.MatchBeginMapSceneScreen;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundBeginMapSceneScreen;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.server.match.R6ServerScoreboard;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.gui.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.*;

/**
 * Client does not have seperated scoreboard or something else. All client data in match are handled here.
 *
 * @author USS_Shenzhou
 */
public class ClientMatch {
    private static Minecraft minecraft = Minecraft.getInstance();
    private static Map map = new Map();
    private static LinkedHashSet<Player> teamOrange;
    private static LinkedHashSet<Player> teamBlue;
    private static Side side;
    private static int bombSiteIndex;
    private static int currentRound;
    private static boolean renderPlayer = true;
    //---dev---
    private static boolean isInMatch;
    private static boolean down;
    private static LinkedHashMap<Player, R6ServerScoreboard.PlayerScoresBase> playerScores = new LinkedHashMap<>();
    private static LinkedList<TeamColor> winnerSides = new LinkedList<>();

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
        ScreenManager.mainMenuScreen.queuingForMatchBar.showGetQueued();
        ScreenManager.mainMenuScreen.stopQueuing();
        ScreenManager.clearLayers();
        ScreenManager.showNewLayerClearBg(new MatchBeginMapSceneScreen());
        //TODO disable food hud
    }

    //----------Start a new Round----------

    public static void newRound(TeamColor attackerColor) {
        currentRound++;
        side = getAllyTeamColor() == attackerColor ? Side.ATTACKER : Side.DEFENDER;
        ScreenManager.clearLayers();
        ScreenManager.showNewLayerClearBg(new RoundBeginMapSceneScreen());
        //RoundPrepareScreen will be called by RoundBeginMapSceneScreen
        //RoundPrepareScreen.newRoundPrepareScreenAndShow();
    }

    public static void preStage() {
        ScreenManager.clearLayers();
        AutoCloseHud.ifPresentFormerThenRemoveAndAdd(new PromptHud.PrepareStage());
        //TODO other HUDs
    }

    public static void actStage() {
        AutoCloseHud.ifPresentFormerThenRemoveAndAdd(new PromptHud.ActStage());
        //TODO other HUDs
    }

    public static void roundEnd(TeamColor winner) {
        //TODO other HUDs
        winnerSides.add(winner);
        HudManager.remove(ScreenManager.playerInfoBarHud);
    }

    public static void afterRound() {
        //TODO
    }

    public static void afterMatch() {
        //TODO
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

    public static TeamColor getAllyTeamColor() {
        return R6Constants.TEST ? TeamColor.ORANGE : teamOrange.contains(minecraft.player) ? TeamColor.ORANGE : TeamColor.BLUE;
    }

    public static TeamColor getAllyTeamColor(Player player) {
        return teamOrange.contains(player) ? TeamColor.ORANGE : TeamColor.BLUE;
    }

    public static boolean isAlly(Player player) {
        return getAllyTeamColor() == getAllyTeamColor(player);
    }

    private static <T> int getIndexOf(Collection<T> collection, T t) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.stream().toList().get(i).equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public static void syncPlayerScore(UUID uuid, R6ServerScoreboard.PlayerScoresBase playerScore) {
        playerScores.put(getPlayerById(uuid), playerScore);
    }

    public static LinkedHashSet<Player> getAttackers() {
        return side == Side.ATTACKER ? getAllies() : getEnemies();
    }

    public static LinkedHashSet<Player> getDefenders() {
        return side == Side.DEFENDER ? getAllies() : getEnemies();
    }

    public static LinkedHashSet<Player> getAllies() {
        return teamOrange.contains(minecraft.player) ? teamOrange : teamBlue;
    }

    public static LinkedHashSet<Player> getEnemies() {
        return teamOrange.contains(minecraft.player) ? teamBlue : teamOrange;
    }

    public static boolean isInMatch() {
        return R6Constants.TEST ? true : isInMatch;
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
        return R6Constants.TEST ? 0 : currentRound;
    }

    public static Side getSide() {
        return R6Constants.TEST ? Side.DEFENDER : side;
    }

    public static int getRoundsLeftToExchange() {
        return 2 - currentRound % 2 + 1;
    }

    public static int getAllyScore() {
        return R6Constants.TEST ? 0 : (int) winnerSides.stream().filter(s -> s == getAllyTeamColor()).count();
    }

    public static int getEnemyScore() {
        return R6Constants.TEST ? 0 : (int) winnerSides.stream().filter(s -> s == getAllyTeamColor().opposite()).count();
    }

    public static Map getMap() {
        return map;
    }

    public static int getNumberInTeam() {
        return R6Constants.TEST ? 2 : getIndexOf(getAllies(), minecraft.player);
    }

    public static void setBombSiteIndex(int bombSiteIndex) {
        ClientMatch.bombSiteIndex = bombSiteIndex;
    }

    public static int getBombSiteIndex() {
        return R6Constants.TEST ? 0 : bombSiteIndex;
    }

    public static int getAttackerAliveAmount() {
        return R6Constants.TEST ? 1 : (int) getAttackers().stream().filter(player -> !player.isSpectator()).count();
    }

    public static int getDefenderAliveAmount() {
        return R6Constants.TEST ? 5 : (int) getDefenders().stream().filter(player -> !player.isSpectator()).count();
    }

    public static boolean isDown() {
        return down;
    }

    public static void setDown(boolean down) {
        ClientMatch.down = down;
    }
}
