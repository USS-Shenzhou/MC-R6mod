package com.ussshenzhou.rainbow6.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapabilities {
    @CapabilityInject(IR6PlayerCapability.class)
    public static Capability<IR6PlayerCapability> R6_PLAYER_CAPABILITY;
}
