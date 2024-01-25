package cn.ussshenzhou.rainbow6.gun.hit;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

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
        //TODO update
        /*Map<ModelLayerLocation, LayerDefinition> models = Minecraft.getInstance().getEntityModels().roots;
        CompletableFuture.runAsync(() -> {
            NeoForgeRegistries.ENTITY_TYPE.getEntries().forEach(entry -> {
                if (entry.getValue() == EntityType.PLAYER) {
                    return;
                }
                var type = entry.getKey();
                var layer = models.get(new ModelLayerLocation(type.location(), "main"));
                if (layer == null) {
                    return;
                }
                var root = layer.mesh.getRoot();
                var head = find("head", root);
                var body = find("body", root);
                if (head == null || body == null) {
                    return;
                }
                NetworkHelper.sendToServer(new UploadEntityModelPacket(type, head, body));
            });
        });*/
    }

    private static PartDefinition find(String key, PartDefinition parent) {
        /*if (parent.children.containsKey(key)) {
            return parent.children.get(key);
        }
        for (PartDefinition p : parent.children.values()) {
            return find(key, p);
        }*/
        return null;
    }
}
