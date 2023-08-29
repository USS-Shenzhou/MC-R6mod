package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.gun.entity.TestBulletEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {

    @Inject(method = "getEntityHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;F)Lnet/minecraft/world/phys/EntityHitResult;",
            at = @At("HEAD"), cancellable = true)
    private static void r6msEntityHitWithPosition(Level pLevel, Entity pProjectile, Vec3 pStartVec, Vec3 pEndVec, AABB pBoundingBox, Predicate<Entity> pFilter, float pInflationAmount, CallbackInfoReturnable<EntityHitResult> cir) {
        if (!(pProjectile instanceof TestBulletEntity)) {
            return;
        }
        double d0 = Double.MAX_VALUE;
        Entity entity = null;
        Optional<Vec3> vec3 = Optional.empty();
        for (Entity entity1 : pLevel.getEntities(pProjectile, pBoundingBox, pFilter)) {
            AABB aabb = entity1.getBoundingBox().inflate((double) pInflationAmount);
            Optional<Vec3> o = aabb.clip(pStartVec, pEndVec);
            if (o.isPresent()) {
                double d1 = pStartVec.distanceToSqr(o.get());
                if (d1 < d0) {
                    vec3 = o;
                    entity = entity1;
                    d0 = d1;
                }
            }
        }
        if (entity == null) {
            cir.setReturnValue(null);
        } else if (vec3.isEmpty()) {
            cir.setReturnValue(new EntityHitResult(entity));
        } else {
            cir.setReturnValue(new EntityHitResult(entity, vec3.get()));
        }
    }
}
