package com.ussshenzhou.rainbow6.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
/**
 * @author USS_Shenzhou
 */
public class R6PlayerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private IR6PlayerCapability r6PlayerCapability;
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        return cap ==ModCapabilities.R6_PLAYER_CAPABILITY ? LazyOptional.of(()-> this.getOrCreateCapability()).cast():LazyOptional.empty();
    }

    @Nonnull
    IR6PlayerCapability getOrCreateCapability(){
        if (r6PlayerCapability==null){
            this.r6PlayerCapability = new R6PlayerCapability("operatorless","teamless");
        }
        return this.r6PlayerCapability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
