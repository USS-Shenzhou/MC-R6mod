package com.ussshenzhou.rainbow6.util;

import com.ussshenzhou.rainbow6.tileentities.BlackMirrorTileEntityRenderer;
import com.ussshenzhou.rainbow6.tileentities.ReinforcementTileEntityRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModTextureStitch {
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }
        event.addSprite(BlackMirrorTileEntityRenderer.BLACK_MIRROR_GLASS_TEXTURE_LOCATION);
        event.addSprite(ReinforcementTileEntityRenderer.TEXTURE_LOCATION);
    }
}
