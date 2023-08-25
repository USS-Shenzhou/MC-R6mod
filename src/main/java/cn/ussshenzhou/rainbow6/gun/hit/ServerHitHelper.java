package cn.ussshenzhou.rainbow6.gun.hit;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author USS_Shenzhou
 */
public class ServerHitHelper {
    private static final HashMap<EntityType<?>, ServerPartDefinition> ENTITY_PART_MAP = new HashMap<>();

    static {
        //Write player model in advance here to avoid client modification.
        put(EntityType.PLAYER, root -> {
            root.addOrReplaceChild("head",
                    ServerCubeListBuilder.create().texOffs(0, 0)
                            .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, ServerCubeDeformation.NONE),
                    ServerPartPose.offset(0.0F, 0.0F, 0.0F)
            );
            root.addOrReplaceChild("body",
                    ServerCubeListBuilder.create().texOffs(16, 16)
                            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, ServerCubeDeformation.NONE),
                    ServerPartPose.offset(0.0F, 0.0F, 0.0F)
            );
        });
    }

    public static void put(EntityType<?> type, Consumer<ServerPartDefinition> addChildren) {
        var part = new ServerPartDefinition(ImmutableList.of(), ServerPartPose.ZERO);
        addChildren.accept(part);
        ENTITY_PART_MAP.put(type, part);
    }

    public static void accept(ServerPlayer sender, EntityType<?> type, ServerPartDefinition head, ServerPartDefinition body) {
        //TODO
    }
}
