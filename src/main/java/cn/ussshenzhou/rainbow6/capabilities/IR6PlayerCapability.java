package cn.ussshenzhou.rainbow6.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
/**
 * @author USS_Shenzhou
 */
public interface IR6PlayerCapability extends INBTSerializable<CompoundNBT> {
    String getOperator();
    String getR6Team();
    String getR6TeamColor();
    void setOperator(String operator);
    void setR6Team(String r6team);
    void setR6TeamColor();
}
