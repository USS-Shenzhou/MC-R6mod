package com.ussshenzhou.rainbow6.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Ee1dEvent {
    @SubscribeEvent
    public static void beforeLivingRenderer(RenderLivingEvent.Pre event) {
        //LogManager.getLogger().info(Minecraft.getInstance().gameSettings.entityDistanceScaling * 160);
        if (true) {
            if (event.getBuffers() instanceof OutlineLayerBuffer) {
                ((OutlineLayerBuffer)event.getBuffers()).setColor(0xb5,0x36,0x33,100);
            } else {

            }
        } else {

        }
    }
}
