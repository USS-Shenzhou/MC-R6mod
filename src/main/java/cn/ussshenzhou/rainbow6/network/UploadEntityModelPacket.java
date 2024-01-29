package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.gun.hit.ClientEntityModelHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ModelUploadHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ServerHitHelper;
import cn.ussshenzhou.rainbow6.gun.hit.ServerPartDefinition;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class UploadEntityModelPacket {
    public final EntityType<?> typeKey;

    @OnlyIn(Dist.CLIENT)
    private PartDefinition head, body;

    public ServerPartDefinition headS, bodyS;

    @OnlyIn(Dist.CLIENT)
    public UploadEntityModelPacket(EntityType<?> typeKey, PartDefinition head, PartDefinition body) {
        this.typeKey = typeKey;
        this.head = head;
        this.body = body;
    }

    @Decoder
    public UploadEntityModelPacket(FriendlyByteBuf buf) {
        typeKey = BuiltInRegistries.ENTITY_TYPE.byId(buf.readInt());
        headS = ModelUploadHelper.readServerPartDefinition(buf);
        bodyS = ModelUploadHelper.readServerPartDefinition(buf);
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(BuiltInRegistries.ENTITY_TYPE.getId(typeKey));
        ModelUploadHelper.writePartDefinition(buf, head);
        ModelUploadHelper.writePartDefinition(buf, body);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {
        if (typeKey == EntityType.PLAYER) {
            return;
        }
        ServerHitHelper.accept((ServerPlayer) context.player().get(), typeKey, headS, bodyS);
    }

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        ClientEntityModelHelper.sendModelsToServer();
    }
}
