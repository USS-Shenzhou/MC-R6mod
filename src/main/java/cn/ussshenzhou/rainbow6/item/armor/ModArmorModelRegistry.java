package cn.ussshenzhou.rainbow6.item.armor;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModArmorModelRegistry {
    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(byShortName("test"), TestArmorModel::createBodyLayer);
    }

    private static ModelLayerLocation byShortName(String name) {
        return new ModelLayerLocation(new ResourceLocation(R6Constants.MOD_ID, name), "main");
    }
}
