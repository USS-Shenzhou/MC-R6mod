package cn.ussshenzhou.rainbow6.gun.hit;

import cn.ussshenzhou.rainbow6.network.UploadEntityModelPacket;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Utils;
import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEntityModelHelper {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onFinishSetup(PlayerEvent.PlayerLoggedInEvent event) {
        if (R6Constants.TEST) {
            sendModelsToServer();
        }
    }

    public static void sendModelsToServer() {
        Map<ModelLayerLocation, LayerDefinition> models = Minecraft.getInstance().getEntityModels().roots;
        CompletableFuture.runAsync(() -> {
            BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
                if (entityType == EntityType.PLAYER) {
                    return;
                }
                var layer = models.get(new ModelLayerLocation(Utils.toResourceLocation(entityType), "main"));
                if (layer == null) {
                    return;
                }
                var root = layer.mesh.getRoot();
                var head = find("head", root);
                var body = find("body", root);
                if (head == null || body == null) {
                    return;
                }
                NetworkHelper.sendToServer(new UploadEntityModelPacket(entityType, head, body));
            });
        });
    }

    private static PartDefinition find(String key, PartDefinition parent) {
        if (parent.children.containsKey(key)) {
            return parent.children.get(key);
        }
        for (PartDefinition p : parent.children.values()) {
            return find(key, p);
        }
        return null;
    }
}
