package cn.ussshenzhou.rainbow6.gun.hit;

import cn.ussshenzhou.rainbow6.network.onlyto.server.UploadEntityModelPacket;
import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityModelListener {

    @SubscribeEvent
    public static void onFinishSetup(EntityRenderersEvent.AddLayers event) {
        sendModelsToServer();
    }

    public static void sendModelsToServer() {
        Map<ModelLayerLocation, LayerDefinition> models = Minecraft.getInstance().getEntityModels().roots;
        CompletableFuture.runAsync(() -> ForgeRegistries.ENTITY_TYPES.getEntries().forEach(entry -> {
            if (entry.getValue() == EntityType.PLAYER) {
                return;
            }
            var type = entry.getKey();
            var layer = models.get(new ModelLayerLocation(type.location(), "main"));
            if (layer == null) {
                return;
            }
            var parts = layer.mesh.getRoot().children;
            NetworkHelper.sendToServer(new UploadEntityModelPacket(type, parts.get("head"), parts.get("body")));
        }));
    }
}
