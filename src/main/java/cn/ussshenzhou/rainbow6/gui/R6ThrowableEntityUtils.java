package cn.ussshenzhou.rainbow6.gui;

import cn.ussshenzhou.rainbow6.entities.DroneEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author USS_Shenzhou
 */
public class R6ThrowableEntityUtils {

    //use ThrowableEntityMovementFix together
    public static class EntityReboundOnBlock {
        public static void reboundOrStop(RayTraceResult oldResult, ProjectileItemEntity entity, double minVelocity, @Nullable SoundEvent reboundSound, double reboundFactor, World world) {
            if (!entity.isOnGround()) {
                Vector3d oldMotion = entity.getMotion();
                double velocity = Math.sqrt(Math.pow(oldMotion.x, 2) + Math.pow(oldMotion.y, 2) + Math.pow(oldMotion.z, 2));
                if (velocity <= minVelocity) {
                    entity.setMotion(0, 0, 0);
                    entity.setOnGround(true);
                } else {
                    Vector3d newMotion = impactReflection(oldResult, oldMotion, reboundFactor);
                    entity.setMotion(newMotion);
                    if (reboundSound != null) {
                        world.playSound((PlayerEntity) null, entity.getPosition(), reboundSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            } else {
                Vector3d oldMotion = entity.getMotion();
                Vector3d newMotion = hitWall(oldResult, oldMotion);
                entity.setMotion(newMotion);
            }
        }

        private static Vector3d impactReflection(RayTraceResult oldResult, Vector3d oldMotion, double reduction) {
            Vector3d newMotion = oldMotion;
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) oldResult;
            Direction direction = blockraytraceresult.getFace();
            switch (direction) {
                case NORTH:
                case SOUTH:
                    newMotion = oldMotion.mul(reduction, reduction, -reduction);
                    break;
                case EAST:
                case WEST:
                    newMotion = oldMotion.mul(-reduction, reduction, reduction);
                    break;
                case UP:
                case DOWN:
                    newMotion = oldMotion.mul(reduction, -reduction, reduction);
                    break;
                default:
                    break;
            }
            return newMotion;
        }

        private static Vector3d hitWall(RayTraceResult oldResult, Vector3d oldMotion) {
            Vector3d newMotion = oldMotion;
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) oldResult;
            Direction direction = blockraytraceresult.getFace();
            switch (direction) {
                case NORTH:
                case SOUTH:
                    newMotion = oldMotion.mul(1, 1, 0);
                    break;
                case EAST:
                case WEST:
                    newMotion = oldMotion.mul(0, 1, 1);
                    break;
                case UP:
                case DOWN:
                    newMotion = oldMotion.mul(1, -1, 1);
                    break;
                default:
                    break;
            }
            return newMotion;
        }
    }

    public static class EntityReboundOnEntity {
        public static void rebound(EntityRayTraceResult oldResult, Entity projectile, Entity entity) {
            Vector3d oldMotion = projectile.getMotion();
            Vector3d newMotion = impactReflection(oldResult, oldMotion, entity);
            projectile.setMotion(newMotion);
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).attackEntityFrom(DamageSource.MAGIC, 1);
            }
        }

        private static Vector3d impactReflection(EntityRayTraceResult oldResult, Vector3d oldMotion, Entity entity) {
            double reduction = 0.18;
            Vector3d newMotion = oldMotion;
            Direction direction = Direction.NORTH;
            AxisAlignedBB aabb = entity.getBoundingBox();
            Vector3d hitPos = oldResult.getHitVec();
            if (hitPos.z == aabb.maxZ) {
                direction = Direction.NORTH;
            } else if (hitPos.z == aabb.minZ) {
                direction = Direction.SOUTH;
            } else if (hitPos.x == aabb.maxX) {
                direction = Direction.WEST;
            } else if (hitPos.x == aabb.minX) {
                direction = Direction.EAST;
            } else if (hitPos.y == aabb.maxY) {
                direction = Direction.UP;
            } else if (hitPos.y == aabb.minY) {
                direction = Direction.DOWN;
            }
            switch (direction) {
                case NORTH:
                case SOUTH:
                    newMotion = oldMotion.mul(reduction, reduction, -reduction);
                    break;
                case EAST:
                case WEST:
                    newMotion = oldMotion.mul(-reduction, reduction, reduction);
                    break;
                case UP:
                case DOWN:
                    newMotion = oldMotion.mul(reduction, -reduction, reduction);
                    break;
                default:
                    break;
            }
            return newMotion;
        }
    }

    /**
     * prevent leak from earth surface.
     * and work with EntityReboundOnBlock
     * see last lines at ThrowableEntity.tick
     * put it after super.tick
     */
    public static class ThrowableEntityMovementFix {
        public static void fix(ThrowableEntity entity, World world, double gravityVelocity) {
            if (entity.isOnGround()) {
                double onBlockHeight = 1;
                double maxError = 0.05;
                boolean flag;
                if (world.isAirBlock(entity.getPosition())) {
                    try {
                        onBlockHeight = world.getBlockState(entity.getPosition()).getCollisionShape(world, entity.getPosition().add(0, -1, 0)).getBoundingBox().maxY;
                    } catch (UnsupportedOperationException ignored) {
                    }
                    flag = Math.abs(entity.getPosY() - (onBlockHeight + entity.getPosition().getY() - 1)) <= maxError;
                } else {
                    AxisAlignedBB aabb = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
                    try {
                        aabb = world.getBlockState(entity.getPosition()).getCollisionShape(world, entity.getPosition()).getBoundingBox();
                    } catch (UnsupportedOperationException ignored) {
                    }
                    BlockPos pos = entity.getPosition();
                    double minX = pos.getX() + aabb.minX;
                    double maxX = pos.getX() + aabb.maxX;
                    double minY = pos.getY() + aabb.minY;
                    double maxY = pos.getY() + aabb.maxY;
                    double minZ = pos.getX() + aabb.minZ;
                    double maxZ = pos.getX() + aabb.maxZ;
                    flag = entity.getPosX() > maxX
                            || entity.getPosX() < minX
                            || entity.getPosY() > maxY
                            || entity.getPosY() < minY
                            || entity.getPosZ() > maxZ
                            || entity.getPosZ() < minZ;
                    if (!flag) {
                        if (entity instanceof DroneEntity){
                            entity.attackEntityFrom(DamageSource.IN_WALL,1);
                        }
                    }
                }
                boolean isOnBlock = flag && !world.isAirBlock(entity.getPosition().add(0, -1, 0));
                entity.setOnGround(isOnBlock);
                if (!entity.hasNoGravity() && isOnBlock) {
                    Vector3d vector3d1 = entity.getMotion();
                    entity.setMotion(vector3d1.x, vector3d1.y + gravityVelocity, vector3d1.z);
                }
            }
        }
    }
}
