package cn.ussshenzhou.rainbow6.dataattachment;

import cn.ussshenzhou.rainbow6.action.BaseAction;
import cn.ussshenzhou.rainbow6.action.Actions;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.List;

/**
 * @author USS_Shenzhou
 */
public class ActionData implements INBTSerializable<CompoundTag> {

    public List<BaseAction> actions = Actions.getAllNewAction();


    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
