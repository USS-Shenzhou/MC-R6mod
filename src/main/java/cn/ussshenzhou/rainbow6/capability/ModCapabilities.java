package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {
    public static final ResourceLocation ACTION = new ResourceLocation(R6Constants.MOD_ID, "action");
    public static final ResourceLocation ANIMATION = new ResourceLocation(R6Constants.MOD_ID, "animation");

    public static final EntityCapability<ActionCapability, Void> ACTION_CAPABILITY = EntityCapability.createVoid(ACTION, ActionCapability.class);
    public static final EntityCapability<AnimationCapability, Void> ANIMATION_CAPABILITY = EntityCapability.createVoid(ANIMATION, AnimationCapability.class);

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerEntity(ACTION_CAPABILITY, EntityType.PLAYER, (player, nothing) -> new ActionCapability());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerCapabilityClient(RegisterCapabilitiesEvent event) {
        event.registerEntity(ANIMATION_CAPABILITY, EntityType.PLAYER, (player, nothing) -> new AnimationCapability());
    }
}
