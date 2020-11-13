package com.ussshenzhou.rainbow6.r6data;

import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public interface IR6playerProperty {

    UUID getUUID();
    void setUUID(UUID uuid);

    /*PlayerEntity getPlayer();
    void setPlayer(PlayerEntity playerEntity);*/

    R6Operator getOperator();
    void setOperator(R6Operator r6Operator);

}
