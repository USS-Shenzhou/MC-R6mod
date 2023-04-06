package cn.ussshenzhou.rainbow6.skin;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkinRegistry {

    @SubscribeEvent
    public static void setItemModelReroute(ModelEvent.ModifyBakingResult event) {
        var map = event.getModels();
        SkinManager.ITEM_SKINS.keySet().forEach(item -> {
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
