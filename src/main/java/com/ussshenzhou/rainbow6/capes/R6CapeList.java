package com.ussshenzhou.rainbow6.capes;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author USS_Shenzhou
 */
public class R6CapeList {
    private static final HashSet<String> r6ThankCapePlayers = new HashSet<String>(){{
        add("Dev");
    }};
    private static final HashSet<String> r6AlphaCapePlayers = new HashSet<String>(){{
    }};

    protected static R6Capes getR6Cape(String playerName){
        if (r6ThankCapePlayers.contains(playerName)) {
            return R6Capes.R6_THANKS_CAPE;
        } else if (r6AlphaCapePlayers.contains(playerName)){
            return R6Capes.R6_ALPHA_CAPE;
        } else {
            return null;
        }
    }


    public enum R6Capes{
        R6_THANKS_CAPE,
        R6_ALPHA_CAPE
    }

}
