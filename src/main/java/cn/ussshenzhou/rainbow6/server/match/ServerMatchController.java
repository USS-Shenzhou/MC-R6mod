package cn.ussshenzhou.rainbow6.server.match;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.network.MatchInitPacket;
import cn.ussshenzhou.rainbow6.server.DelayedTask;
import cn.ussshenzhou.rainbow6.server.DelayedTaskManager;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.UUID;

import static cn.ussshenzhou.rainbow6.util.MapTopViewHelper.minecraft;

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
        match.teamOrange.forEach(player -> uuids.add(player.getUUID()));
        match.teamBlue.forEach(player -> uuids.add(player.getUUID()));
        //TODO save player's inventory and GlobalPos.
        match.sendPacketsToEveryOne(new MatchInitPacket(match.map, uuids));
        DelayedTaskManager.arrange(20, this::newRound);
    }

    public void newRound() {
        //init roundNumber
        match.currentRoundNumber++;
        //choose a&d
        if (match.currentRoundNumber == 1) {
            match.attackerColor = Math.random() < 0.5 ? TeamColor.ORANGE : TeamColor.BLUE;
        } else if (match.currentRoundNumber % 2 == 1) {
            match.attackerColor = match.attackerColor.opposite();
        }

    }

    public void tick() {

    }
}
