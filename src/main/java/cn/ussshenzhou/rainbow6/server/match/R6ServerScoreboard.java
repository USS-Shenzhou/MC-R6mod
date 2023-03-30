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
    protected ServerMatch match;
    protected LinkedList<Side> winnerSides = new LinkedList<>();
    protected LinkedList<LinkedList<Operator>> operators = new LinkedList<>();
    protected LinkedHashMap<ServerPlayer, PlayerData> playerDataMaps = new LinkedHashMap<>();

    public R6ServerScoreboard(ServerMatch match) {
        this.match = match;
    }

    public void newRound() {
        match.forEachPlayer(player -> playerDataMaps.put(player, new PlayerData()));
    }

    public void preparationStage() {
        operators.add(new LinkedList<>(match.chosenOperators.values()));
    }

    public void afterMatch() {
        match = null;
    }

    public void playerDownedBy(ServerPlayer down, ServerPlayer shooter) {
        playerDataMaps.get(shooter).addScore(75);
    }

    public void playerKilledBy(ServerPlayer down, ServerPlayer shooter) {
        playerDataMaps.get(down).addDeath();
        playerDataMaps.get(shooter).addScore(100);
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

        public void addAssist() {
            assists++;
        }

        public void addDeath() {
            deaths++;
        }
    }
}
