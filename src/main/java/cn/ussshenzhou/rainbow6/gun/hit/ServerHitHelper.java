package cn.ussshenzhou.rainbow6.gun.hit;

import cn.ussshenzhou.rainbow6.gun.entity.TestBulletEntity;
import cn.ussshenzhou.rainbow6.util.ModDamageSources;
import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
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
                    ServerCubeListBuilder.create()
                            .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, ServerCubeDeformation.NONE),
                    ServerPartPose.ZERO
            );
            root.addOrReplaceChild("body",
                    ServerCubeListBuilder.create()
                            .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, ServerCubeDeformation.NONE),
                    ServerPartPose.ZERO
            );
        });
    }

    public static void put(EntityType<?> type, Consumer<ServerPartDefinition> addChildren) {
        var entityRoot = new ServerPartDefinition(ImmutableList.of(), ServerPartPose.ZERO);
        addChildren.accept(entityRoot);
        ENTITY_PART_MAP.put(type, entityRoot);
    }

    public static void accept(ServerPlayer sender, EntityType<?> type, ServerPartDefinition head, ServerPartDefinition body) {
        boolean isFromOp = sender.hasPermissions(4);
        if (ENTITY_PART_MAP.containsKey(type) && !isFromOp) {
            return;
        }
        var entityRoot = new ServerPartDefinition(ImmutableList.of(), ServerPartPose.ZERO);
        entityRoot.children.put("head", head);
        entityRoot.children.put("body", body);
        ENTITY_PART_MAP.put(type, entityRoot);
    }

    public static void aSyncHit(TestBulletEntity bullet, EntityHitResult hit) {
        CompletableFuture.runAsync(() -> {
            //head
            float damage = 1000;
            var hitAt = calculateHitAt(bullet, hit);
            LogUtils.getLogger().warn("{}", hitAt);
            if (hitAt != HitAt.HEAD) {
                var distance = (float) bullet.spawnPos.distanceTo(hit.getLocation());
                damage = bullet.gunModifier.getDamageDistanceDecayed(bullet.gun.getFixedProperty(), distance);
                switch (hitAt) {
                    case LIMB -> {
                        //limb
                        //CSRX 300: 0.6
                        damage *= 0.75f;
                    }
                    case NON_LIVING -> {
                        //non-living
                        float f = damage;
                        bullet.level().getServer().execute(() ->
                                bullet.hitNonLiving(hit, f));
                    }
                    //no-correction: body, non-exist
                }
            }
            float f1 = damage;
            bullet.level().getServer().execute(() ->
                    hit.getEntity().hurt(ModDamageSources.shot(bullet.shooter, hit.getEntity()), f1));
        });
    }

    /**
     * All length in this method should be in 1/16 block.
     */
    protected static HitAt calculateHitAt(TestBulletEntity bullet, EntityHitResult hit) {
        if (!(hit.getEntity() instanceof LivingEntity victim)) {
            return HitAt.NON_LIVING;
        }
        var modelRoot = ENTITY_PART_MAP.get(victim.getType());
        if (modelRoot == null) {
            return HitAt.NON_EXIST;
        }
        var hitPos = hit.getLocation().toVector3f();
        var entityPos = victim.getPosition(1).toVector3f();
        float maxD = Math.max(victim.getBbWidth(), victim.getBbHeight()) * 16;
        Matrix4f matrix4f = new Matrix4f();
        hitPos = absToRel(matrix4f, hitPos, entityPos, victim)
                //model position correction
                .add(0, -victim.getBbHeight() / 2, 0)
                .mul(16);
        while (hitPos.distanceSquared(0, 0, 0) > maxD * maxD) {
            hitPos.mul(0.9f);
        }
        var hitDirection = absToRel(matrix4f, bullet.getDeltaMovement().toVector3f(), new Vector3f(), victim).normalize();
        var p = new Vector3f(hitPos);
        var v = new Vector3f(hitDirection);
        //TODO body/head x/z rot - need send packet
        matrix4f.identity()
                .rotate(-victim.yHeadRot, 0, 1, 0)
                .transformPosition(p);
        matrix4f.transformPosition(v);
        if (hitPart(modelRoot.children.get("head"), p, v, maxD)) {
            return HitAt.HEAD;
        }
        if (hitPart(modelRoot.children.get("body"), hitPos, hitDirection, maxD)) {
            return HitAt.BODY;
        }
        return HitAt.LIMB;
    }

    protected static boolean hitPart(ServerPartDefinition part, Vector3f hitPos, Vector3f hitDirection, float maxD) {
        float step = 1f;
        var h = new Vector3f(hitPos);
        var v = new Vector3f(hitDirection).mul(step);
        for (float i = 0; i < maxD / 2; i += step) {
            for (ServerCubeDefinition cube : part.cubes) {
                if (hitCube(cube, h)) {
                    return true;
                }
            }
            h.add(v);
        }
        for (ServerPartDefinition p : part.children.values()) {
            if (hitPart(p, hitPos, hitDirection, maxD)) {
                return true;
            }
        }
        return false;
    }

    protected static boolean hitCube(ServerCubeDefinition cube, Vector3f pos) {
        //TODO support grow
        var o = cube.origin;
        var s = cube.dimensions;
        return pos.x >= o.x
                && pos.y >= -o.y
                && pos.z >= o.z
                && pos.x <= o.x + s.x
                && pos.y <= -o.y + s.y
                && pos.z <= o.z + s.z;
    }

    protected static Vector3f absToRel(Matrix4f matrix4f, Vector3f abs, Vector3f center, LivingEntity centerEntity) {
        var rel = abs.add(center.mul(-1));
        matrix4f.identity()
                .translate(0, 0, 0)
                .rotate(-centerEntity.yBodyRot, 0, 1, 0)
                .transformPosition(rel);
        return rel;
    }

    public enum HitAt {
        HEAD,
        BODY,
        LIMB,
        NON_LIVING,
        NON_EXIST
    }
}
