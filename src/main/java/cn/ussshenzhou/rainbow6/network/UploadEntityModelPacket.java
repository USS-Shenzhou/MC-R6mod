package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gun.hit.ClientEntityModelHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ModelUploadHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ServerHitHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ServerPartDefinition;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class UploadEntityModelPacket {
    public final ResourceKey<EntityType<?>> typeKey;

    @OnlyIn(Dist.CLIENT)
    private PartDefinition head, body;

    public ServerPartDefinition headS, bodyS;

    @OnlyIn(Dist.CLIENT)
    public UploadEntityModelPacket(ResourceKey<EntityType<?>> typeKey, PartDefinition head, PartDefinition body) {
        this.typeKey = typeKey;
        this.head = head;
        this.body = body;
    }

    @Decoder
    public UploadEntityModelPacket(FriendlyByteBuf buf) {
        typeKey = buf.readResourceKey(Registries.ENTITY_TYPE);
        headS = ModelUploadHelper.readServerPartDefinition(buf);
        bodyS = ModelUploadHelper.readServerPartDefinition(buf);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceKey(typeKey);
        ModelUploadHelper.writePartDefinition(buf, head);
        ModelUploadHelper.writePartDefinition(buf, body);
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            var type = ForgeRegistries.ENTITY_TYPES.getValue(typeKey.location());
            if (type == null || type == EntityType.PLAYER) {
                return;
            }
            ServerHitHelper.accept(context.get().getSender(), type, headS, bodyS);
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {
        ClientEntityModelHelper.sendModelsToServer();
    }
}
