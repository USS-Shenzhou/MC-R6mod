package com.ussshenzhou.rainbow6.r6properties;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class R6PlayerProperty {
    private UUID uuid;
    private R6Operator r6Operator = R6Operator.PLAYER;
    private R6Team r6Team = R6Team.NONE;

    public R6PlayerProperty(UUID uuid,R6Operator r6Operator,R6Team r6Team){
        this.uuid = uuid;
        this.r6Operator = r6Operator;
        this.r6Team = r6Team;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public R6Operator getOperator() {
        return this.r6Operator;
    }

    public void setOperator(R6Operator r6Operator) {
        this.r6Operator = r6Operator;
    }

    public R6Team getR6Team() {
        return this.r6Team;
    }

    public void setR6Team(R6Team r6Team) {
        this.r6Team = r6Team;
    }
}
