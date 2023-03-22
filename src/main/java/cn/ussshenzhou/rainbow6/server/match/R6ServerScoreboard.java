package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.Side;
import net.minecraft.server.level.ServerPlayer;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author USS_Shenzhou
 */
public class R6ServerScoreboard {
    private ServerMatch match;
    private LinkedList<Side> winnerSides = new LinkedList<>();
    private LinkedList<LinkedList<Operator>> operators = new LinkedList<>();
    private LinkedHashMap<ServerPlayer, PlayerData> playerDataMap = new LinkedHashMap<>();

    public R6ServerScoreboard(ServerMatch match) {
        this.match = match;
    }

    public void afterMatch(){
        match = null;
    }

    public static class PlayerData {
        private int score = 0;
        private int kills = 0;
        private int assists = 0;
        private int deaths = 0;

        public void addScore(int score) {
            this.score += score;
        }

        public void addKill() {
            kills++;
        }

        public void addAssists() {
            assists++;
        }

        public void addDeaths() {
            deaths++;
        }
    }
}
