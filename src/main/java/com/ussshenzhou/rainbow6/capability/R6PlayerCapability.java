package com.ussshenzhou.rainbow6.capability;

import net.minecraft.nbt.CompoundNBT;

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

    public R6PlayerCapability(String operator,String r6Team){
        this.operator = operator;
        this.r6Team = r6Team;
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
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public void setR6Team(String r6team) {
        this.r6Team = r6team;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("operator",this.operator);
        compoundNBT.putString("r6team",this.r6Team);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.operator = nbt.getString("operator");
        this.r6Team = nbt.getString("r6team");
    }
}
