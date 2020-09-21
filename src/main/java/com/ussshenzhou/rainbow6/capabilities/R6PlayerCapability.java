package com.ussshenzhou.rainbow6.capabilities;

import net.minecraft.nbt.CompoundNBT;
/**
 * @author USS_Shenzhou
 */
public class R6PlayerCapability implements IR6PlayerCapability {
    /**
     * use the lower case of operator's nickname.
     * for common player in server, use "operatorless".
     */
    private String operator;

    /**
     * use "attacker" or "defender" or "r6_spectator".
     * for common player in server, use "teamless"
     *
     */
    private String r6Team;

    /**
     * use "blue" or "orange" or "white"
     */
    private String r6TeamColor;


    public R6PlayerCapability(String operator,String r6Team,String r6TeamColor){
        this.operator = operator;
        this.r6Team = r6Team;
        this.r6TeamColor = r6TeamColor;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public String getR6Team() {
        return r6Team;
    }

    @Override
    public String getR6TeamColor() {
        return r6TeamColor;
    }

    @Override
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public void setR6Team(String r6team) {
        this.r6Team = r6team;
    }

    @Override
    public void setR6TeamColor() {
        this.r6TeamColor = r6TeamColor;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("operator",this.operator);
        compoundNBT.putString("r6team",this.r6Team);
        compoundNBT.putString("r6teamcolor",this.r6TeamColor);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.operator = nbt.getString("operator");
        this.r6Team = nbt.getString("r6team");
        this.r6TeamColor = nbt.getString("r6teamcolor");
    }
}
