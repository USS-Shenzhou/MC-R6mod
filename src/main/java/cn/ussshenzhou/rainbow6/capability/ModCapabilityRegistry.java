package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCapabilityRegistry {
    public static final Capability<ActionCapability> ACTION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<AnimationCapability> ANIMATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final ResourceLocation ACTION = new ResourceLocation(R6Constants.MOD_ID, "action");
    public static final ResourceLocation ANIMATION = new ResourceLocation(R6Constants.MOD_ID, "animation");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            event.addCapability(ACTION, new ActionCapability.Provider());

            if (player.level().isClientSide) {
                event.addCapability(ANIMATION, new AnimationCapability.Provider());

            }
        }
    }
}
