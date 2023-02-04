package cn.ussshenzhou.rainbow6.client.match;

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

    public static String mapName(){
        //TODO
        return "Test";
    }

    public static int getRoundNumber(){
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
}
