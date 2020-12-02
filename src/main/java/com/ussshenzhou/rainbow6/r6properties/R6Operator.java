package com.ussshenzhou.rainbow6.r6properties;

/**
 * @author USS_Shenzhou
 */

public enum R6Operator {
    //players in server but not playing R6
    PLAYER("player"),
    //before players choosing or enemy players undetected
    UNKNOWN("unknown"),
    //R6 operators
    MIRA("mira"),

    ;
    private final String Operator;

    private R6Operator(String operator){
        this.Operator=operator;
    }
}