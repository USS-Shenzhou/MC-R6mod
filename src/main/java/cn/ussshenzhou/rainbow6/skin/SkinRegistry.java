package cn.ussshenzhou.rainbow6.skin;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SkinRegistry {

    @SubscribeEvent
    public static void setItemModelReroute(ModelEvent.ModifyBakingResult event) {
        var map = event.getModels();
        SkinManager.ITEM_SKINS.keySet().forEach(item -> {
            if (item.equals(SkinManager.UNIVERSAL_SKIN)) {
                return;
            }
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item, "inventory");
            BakedModel defaultModel = map.get(modelResourceLocation);
            map.put(modelResourceLocation, new BakedModelWrapper<>(defaultModel) {
                @Override
                public ItemOverrides getOverrides() {
                    return new SkinItemOverridesHandler();
                }
            });
        });
    }

    @SubscribeEvent
    public static void add(ModelEvent.RegisterAdditional event) {
        SkinManager.SKINS.values().forEach(skin -> event.register(skin.getModelLocation()));
    }
}
