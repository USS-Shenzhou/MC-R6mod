package cn.ussshenzhou.rainbow6.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
/**
 * @author USS_Shenzhou
 */
public class ModCapabilities {
    @CapabilityInject(IR6PlayerCapability.class)
    public static Capability<IR6PlayerCapability> R6_PLAYER_CAPABILITY;
}
