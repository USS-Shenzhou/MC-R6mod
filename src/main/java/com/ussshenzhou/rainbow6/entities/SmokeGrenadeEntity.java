package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.gui.R6ThrowableEntityUtils;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.particles.SmokeGrenadeParticleData;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;

/**
 * @author USS_Shenzhou
 */
public class SmokeGrenadeEntity extends ProjectileItemEntity {
    public SmokeGrenadeEntity(EntityType<SmokeGrenadeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.smokeGrenade;
    }

    private static DataParameter<Integer> timeCountDown = EntityDataManager.createKey(SmokeGrenadeEntity.class, DataSerializers.VARINT);

    @Override
    protected void registerData() {
        this.dataManager.register(timeCountDown, 60);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("timwcountdown", this.dataManager.get(timeCountDown));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("timecountdown")) {
            this.dataManager.set(timeCountDown, compound.getInt("timecountdown"));
        }
    }

    public void setTimeCountDown(int aTimeCountDown) {
        this.dataManager.set(timeCountDown, aTimeCountDown);
    }


    private final int LAST_TIME = -240;

    @Override
    public void tick() {
        if (this.dataManager.get(timeCountDown) == 0) {
            world.playSound((PlayerEntity) null, this.getPosX(), this.getPosY(), this.getPosZ(), ModSounds.REMOTEGASGRENADE_EXPLODE, SoundCategory.PLAYERS, 0.7f, 1.0f);
        }

        if (this.dataManager.get(timeCountDown) <= 0) {
            if (world.isRemote) {
                world.addParticle(new SmokeGrenadeParticleData(new Vector3d(0, 0, 0), new Color(0), 0), this.getPosX() + rand(), this.getPosY() + rand(), this.getPosZ() + rand(), 0, 0, 0);
            }
        }

        if (this.onGround) {
            this.dataManager.set(timeCountDown, this.dataManager.get(timeCountDown) - 1);
        }

        if (this.dataManager.get(timeCountDown) <= LAST_TIME) {
            this.remove();
        }

        super.tick();
        R6ThrowableEntityUtils.ThrowableEntityMovementFix.fix(this,world,this.getGravityVelocity());
    }

    private double rand() {
        if (Math.random() * 10 < 5) {
            return Math.random() * 0.5;
        } else {
            return Math.random() * -0.5;
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            R6ThrowableEntityUtils.EntityReboundOnBlock.reboundOrStop(result, this, 0.1d, ModSounds.FRAGGRENADE_TOUCH, 0.25d, world);
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
    protected void updatePitchAndYaw() {

    }
    public void setRandomRotation() {
        this.rotationYaw = (float) (Math.random()*360);
        this.rotationPitch = (float) (Math.random()*360);
    }
}
