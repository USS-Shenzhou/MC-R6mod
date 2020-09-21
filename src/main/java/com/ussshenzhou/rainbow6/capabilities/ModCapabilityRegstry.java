package com.ussshenzhou.rainbow6.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;
/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilityRegstry {
    @SubscribeEvent
    public static void onCapabilityReg(FMLCommonSetupEvent event){
        CapabilityManager.INSTANCE.register(
                IR6PlayerCapability.class,
                new Capability.IStorage<IR6PlayerCapability>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IR6PlayerCapability> capability, IR6PlayerCapability instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IR6PlayerCapability> capability, IR6PlayerCapability instance, Direction side, INBT nbt) {

                    }
                },
                ()->null
        );
    }
}
