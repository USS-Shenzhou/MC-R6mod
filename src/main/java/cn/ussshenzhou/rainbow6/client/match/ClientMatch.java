package cn.ussshenzhou.rainbow6.client.match;

import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.util.Sides;
import cn.ussshenzhou.rainbow6.util.TeamColor;

/**
 * @author USS_Shenzhou
 */
public class ClientMatch {

    public static TeamColor getTeamColor(){
        //TODO
        return TeamColor.BLUE;
    }

    public static int getCurrentRoundNumber(){
        return 1;
        //TODO
    }

    public static Sides getSide(){
        return Sides.ATTACKER;
        //TODO
    }

    public static int getRoundsLeftToExchange(){
        return 2;
        //TODO
    }

    public static int getAllyScore(){
        //TODO
        return 0;
    }

    public static int getEnemyScore(){
        return 0;
    }

    public static Map getMap(){
        return new Map();
        //TODO
    }
}
