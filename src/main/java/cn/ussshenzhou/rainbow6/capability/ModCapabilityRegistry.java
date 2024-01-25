package cn.ussshenzhou.rainbow6.capability;

import net.neoforged.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCapabilityRegistry {
    //TODO update
    /*public static final Capability<ActionCapability> ACTION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
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
    }*/
}
