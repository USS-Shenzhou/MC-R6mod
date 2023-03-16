package cn.ussshenzhou.rainbow6.client.match;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen;
import cn.ussshenzhou.rainbow6.data.Map;
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

    //----------Start a new Match----------

    public static void init(Map m, ArrayList<UUID> u) {
        map = m;
        teamOrange = new LinkedHashSet<>();
        Level level = minecraft.level;
        u.subList(0, 5).forEach(uuid -> teamOrange.add(level.getPlayerByUUID(uuid)));
        u.subList(5, 10).forEach(uuid -> teamBlue.add(level.getPlayerByUUID(uuid)));
        if (teamOrange.size() != 5 || teamBlue.size() != 5) {
            //TODO
        }
        notifyGui();
    }

    private static void notifyGui() {
        ScreenManager.mainMenuScreenBuffer.queuingForMatchBar.showGetQueued();
        ScreenManager.mainMenuScreenBuffer.stopQueuing();
    }

    //----------Start a new Round----------

    public static void newRound(TeamColor attackerColor) {
        currentRound++;
        side = getTeamColor() == attackerColor ? Side.ATTACKER : Side.DEFENDER;
        RoundPrepareScreen.newRoundPrepareScreenAndShow();
    }

    //--------------------

    public static void stopRenderPlayer() {
        renderPlayer = false;
    }

    public static void resumeRenderPlayer() {
        renderPlayer = true;
    }

    public static boolean isRenderPlayer() {
        return renderPlayer;
    }

    public static TeamColor getTeamColor() {
        return teamOrange.contains(minecraft.player) ? TeamColor.ORANGE : TeamColor.BLUE;
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
        //TODO billboard
        return 0;
    }

    public static int getEnemyScore() {
        return 0;
        //TODO billboard
    }

    public static Map getMap() {
        return map;
    }

    public static int getNumberInTeam() {
        //---dev---
        //return getIndexOf(teamOrange.contains(minecraft.player) ? teamOrange : teamBlue, minecraft.player);
        return 2;
    }

    private static <T> int getIndexOf(Collection<T> collection, T t) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.stream().toList().get(i).equals(t)) {
                return i;
            }
        }
        return -1;
    }

    public static void setBombSiteIndex(int bombSiteIndex) {
        ClientMatch.bombSiteIndex = bombSiteIndex;
    }

    public static int getBombSiteIndex() {
        return bombSiteIndex;
    }

    public static int getAttackerAliveAmount() {
        //TODO billboard
        return 1;
    }

    public static int getDefenderAliveAmount() {
        //TODO billboard
        return 5;
    }
}
