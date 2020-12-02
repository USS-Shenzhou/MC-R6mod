package com.ussshenzhou.rainbow6.r6properties;

/**
 * @author USS_Shenzhou
 */

public enum R6Team {
    //players in server but not playing R6
    NONE("none"),
    //players playing R6
    ATTACKER("attacker"),
    DEFENDER("defender"),

    ;
    private final String Team;

    private R6Team(String team){
        this.Team=team;
    }
}
