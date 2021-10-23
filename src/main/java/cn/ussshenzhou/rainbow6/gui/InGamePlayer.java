package cn.ussshenzhou.rainbow6.gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class InGamePlayer {

    private UUID playerUUID;
    private String team;
    private String operator;
    public InGamePlayer(UUID playerUUID, String team, String operator){
        this.playerUUID = playerUUID;
        this.team = team;
        this.operator = operator;
    }

    public UUID getPlayerUUID(){
        return this.playerUUID;
    }

    public String getTeam(){
        return this.team;
    }

    public String getOperator(){
        return this.operator;
    }
}
