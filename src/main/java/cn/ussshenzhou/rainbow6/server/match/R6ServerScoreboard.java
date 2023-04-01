package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.network.onlyto.client.SyncR6ScoreboardPacket;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author USS_Shenzhou
 */
public class R6ServerScoreboard {
    protected ServerMatch match;
    protected LinkedList<TeamColor> winnerSides = new LinkedList<>();
    protected LinkedList<LinkedList<Operator>> operators = new LinkedList<>();
    protected LinkedHashMap<ServerPlayer, PlayerScoresServer> playerScores = new LinkedHashMap<>();

    public R6ServerScoreboard(ServerMatch match) {
        this.match = match;
    }

    public void newRound() {
        match.forEachPlayer(player -> playerScores.put(player, new PlayerScoresServer(player)));
    }

    public void preparationStage() {
        operators.add(new LinkedList<>(match.chosenOperators.values()));
    }

    public void afterMatch() {
        match = null;
    }

    public void playerDownedBy(ServerPlayer down, ServerPlayer shooter) {
        playerScores.get(shooter).addScore(75);
    }

    public void playerKilledBy(ServerPlayer down, ServerPlayer shooter) {
        playerScores.get(down).addDeath();
        playerScores.get(shooter).addScore(100);
    }


    public static class PlayerScoresBase {
        protected int score = 0;
        protected int kills = 0;
        protected int assists = 0;
        protected int deaths = 0;

        public PlayerScoresBase() {
        }

        public PlayerScoresBase(FriendlyByteBuf buf) {
            score = buf.readInt();
            kills = buf.readInt();
            assists = buf.readInt();
            deaths = buf.readInt();
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeInt(score);
            buf.writeInt(kills);
            buf.writeInt(assists);
            buf.writeInt(deaths);
        }
    }

    public class PlayerScoresServer extends PlayerScoresBase {
        private final Player owner;

        public PlayerScoresServer(Player owner) {
            this.owner = owner;
        }

        public void addScore(int score) {
            this.score += score;
            syncToClient();
        }

        public void addKill() {
            kills++;
            syncToClient();
        }

        public void addAssist() {
            assists++;
            syncToClient();
        }

        public void addDeath() {
            deaths++;
            syncToClient();
        }

        private void syncToClient() {
            match.sendPacketsToEveryOne(new SyncR6ScoreboardPacket(owner.getUUID(), this));
        }
    }
}
