package com.ussshenzhou.rainbow6.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
/**
 * @author USS_Shenzhou
 */
public interface IR6PlayerCapability extends INBTSerializable<CompoundNBT> {
    String getOperator();
    String getR6Team();
    void setOperator(String operator);
    void setR6Team(String r6team);
}
