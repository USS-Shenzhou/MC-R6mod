package com.ussshenzhou.rainbow6.entities;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityRenderRegistry {
    @SubscribeEvent
    public static void registryEntityRenders(final FMLClientSetupEvent event) {
        //RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.c4entitytype, c4renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.impactgrenadeentitytype, ImpactGrenadeEntityRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.gumineentitytype,guminerenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.RemoteGasGrenadeEntityType,RemoteGasGrenadeRenderer::new);
    }
}
