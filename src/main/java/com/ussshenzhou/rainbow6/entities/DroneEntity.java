package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.gui.R6ThrowableEntityUtils;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.network.R6DroneControllerPack;
import com.ussshenzhou.rainbow6.network.R6DroneControllerPackSend;
import com.ussshenzhou.rainbow6.network.R6DroneMotionPack;
import com.ussshenzhou.rainbow6.network.R6DroneMotionPackSend;
import com.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class DroneEntity extends ProjectileItemEntity implements Comparable<DroneEntity> {
    private Entity owner;
    private Boolean leftInputDown = false;
    private Boolean rightInputDown = false;
    private Boolean forwardInputDown = false;
    private Boolean backInputDown = false;
    private Boolean jumpInputDown = false;
    private Vector3d prevMotion = Vector3d.ZERO;

    public DroneEntity(EntityType<DroneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public DroneEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
        owner = livingEntityIn;
        this.onGround = false;
    }

    private UUID controller = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final double maxAccelerationSpeed = 0.5;
    private int jumpCD = 0;

    @Override
    public void tick() {
        super.tick();
        R6ThrowableEntityUtils.ThrowableEntityMovementFix.fix(this, world, this.getGravityVelocity());
        //see LivingEntity.travel
        if (this.onGround) {
            float slipperiness = world.getBlockState(this.getPosition()).getSlipperiness(world, this.getPositionUnderneath(), this);
            this.setMotion(this.getMotion().mul(slipperiness * 0.89, 1, slipperiness * 0.89));
        }
        //control
        if (world.isRemote && !this.controller.equals(UUID.fromString("00000000-0000-0000-0000-000000000000")) && this.onGround) {
            this.controlDrone();
        }
        if (world.isRemote) {
            this.syncMotionAndRotation();
        }
        //drone hit sound
        Vector3d motion = this.getMotion();
        boolean isBigAcceleration = Math.abs(motion.x - prevMotion.x) > maxAccelerationSpeed || Math.abs(motion.y - prevMotion.y) > maxAccelerationSpeed || Math.abs(motion.z - prevMotion.z) > maxAccelerationSpeed;
        if (this.ticksExisted > 2 && isBigAcceleration) {
            world.playSound(null, this.getPosition(), ModSounds.DRONE_HIT, SoundCategory.PLAYERS, 1.0f, (float) (Math.random() * 0.18 + 1));
        }
        prevMotion = motion;
        //drone moving sound
        if (world.isRemote) {
            if (this.onGround) {
                double velocity = Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
                DroneEntityClientProxy.clientProxy(this, velocity, this.ticksExisted);
            }
        }
        //jump cd
        if (jumpCD > 0) {
            jumpCD--;
        }
    }

    /**
     * see BoatEntity
     */
    @OnlyIn(Dist.CLIENT)
    public void updateInputs(boolean aleftInputDown, boolean arightInputDown, boolean aforwardInputDown, boolean abackInputDown, boolean ajumpInputDown) {
        leftInputDown = aleftInputDown;
        rightInputDown = arightInputDown;
        forwardInputDown = aforwardInputDown;
        backInputDown = abackInputDown;
        jumpInputDown = ajumpInputDown;
    }

    private final float speed = 0.08f;
    private final float jumpSpeed = 0.22f;

    @OnlyIn(Dist.CLIENT)
    public void controlDrone() {
        float f = 0;
        if (forwardInputDown) {
            f += speed;
        }

        if (backInputDown) {
            f -= speed;
        }
        double x = (double) (MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)) * f);
        double y = 0;
        double z = (double) (MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)) * f);

        float l = 0;
        if (leftInputDown) {
            l += speed;
        }

        if (rightInputDown) {
            l -= speed;
        }

        x += (double) (MathHelper.sin((this.rotationYaw + 90) * ((float) Math.PI / 180F)) * l);
        y += 0;
        z += (double) (MathHelper.cos((this.rotationYaw + 90) * ((float) Math.PI / 180F)) * l);

        float j = 0;
        if (jumpInputDown) {
            if (jumpCD == 0) {
                j += jumpSpeed;
                jumpCD += 60;
                y += 0.18;
            } else {
                //
            }
        }
        float i = this.rotationPitch < 0 ? j : MathHelper.cos(this.rotationPitch * ((float) Math.PI / 180F)) * j * 0.8f;
        x += (double) (MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)) * i);
        y += this.rotationPitch < 0 ? 0 : MathHelper.sin(this.rotationPitch * ((float) Math.PI / 180F)) * j * 1.2f;
        z += (double) (MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)) * i);

        this.setMotion(this.getMotion().add(x, y, z));
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }


    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            R6ThrowableEntityUtils.EntityReboundOnBlock.reboundOrStop(result, this, 0.1d, null, 0.25d, world);
            this.markVelocityChanged();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) result;
            Entity entity = entityRayTraceResult.getEntity();
            if (entity != this.getShooter()) {
                R6ThrowableEntityUtils.EntityReboundOnEntity.rebound(entityRayTraceResult, this, entity);
                this.markVelocityChanged();
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.droneItem;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int compareTo(DroneEntity o) {
        return this.getUniqueID().compareTo(o.entityUniqueID);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote) {
            this.markVelocityChanged();
            this.world.playSound((PlayerEntity) null, getPosition(), SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.remove();
        } else {
            this.world.addParticle(ParticleTypes.CLOUD, this.getPosX(), this.getPosY(), this.getPosZ(), 0, 0, 0);
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void updatePitchAndYaw() {

    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        this.rotationPitch = 0;
    }

    @Override
    public float getYaw(float partialTicks) {
        return partialTicks == 1.0F ? -this.rotationYaw : MathHelper.lerp(partialTicks, -this.prevRotationYaw, -this.rotationYaw);
    }

    @Override
    public float getPitch(float partialTicks) {
        return partialTicks == 1.0F ? -this.rotationPitch : MathHelper.lerp(partialTicks, -this.prevRotationPitch, -this.rotationPitch);
    }

    public void setRotation(double yaw, double pitch) {
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.rotationYaw = (float) yaw;
        this.rotationPitch = (float) pitch;
    }

    public void setController(UUID controller) {
        this.controller = controller;
        if (world.isRemote) {
            R6DroneControllerPackSend.channel.sendToServer(new R6DroneControllerPack(controller, world.getDimensionKey().getLocation().getPath(), this.getEntityId()));
        }
    }

    public void syncMotionAndRotation() {
        R6DroneMotionPackSend.channel.sendToServer(new R6DroneMotionPack(this.getMotion().x, this.getMotion().y, this.getMotion().z, world.getDimensionKey().getLocation().getPath(), this.getEntityId(), this.rotationYaw, this.rotationPitch));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (controller.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
            super.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
        } else {
            if (Minecraft.getInstance().player != null && this.controller != Minecraft.getInstance().player.getUniqueID()) {
                this.setRotation(yaw, pitch);
                this.setMotion(x - this.getPosX(), y - this.getPosY(), z - this.getPosZ());
            }
        }
    }

    public UUID getController() {
        return controller;
    }
}
