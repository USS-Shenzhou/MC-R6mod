package cn.ussshenzhou.rainbow6.entities;

import cn.ussshenzhou.rainbow6.gui.R6ThrowableEntityUtils;
import cn.ussshenzhou.rainbow6.items.ModItems;
import cn.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author USS_Shenzhou
 */
public class FragGrenadeEntity extends ProjectileItemEntity {

    public FragGrenadeEntity(EntityType<FragGrenadeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public FragGrenadeEntity(EntityType<? extends FragGrenadeEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }


    private static DataParameter<Integer> timeCountDown = EntityDataManager.createKey(FragGrenadeEntity.class, DataSerializers.VARINT);

    @Override
    protected Item getDefaultItem() {
        return ModItems.fragGrenade;
    }

    @Override
    public void tick() {
        if (this.dataManager.get(timeCountDown) <= 0) {
            this.explode();
            this.remove();
        } else {
            this.dataManager.set(timeCountDown, this.dataManager.get(timeCountDown) - 1);
        }
        super.tick();
        R6ThrowableEntityUtils.ThrowableEntityMovementFix.fix(this, world, this.getGravityVelocity());
    }


    public void explode() {
        if (!this.world.isRemote) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 2f, Explosion.Mode.DESTROY);
            this.remove();
        }
    }

    @Override
    protected void registerData() {
        this.dataManager.register(timeCountDown, 90);
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

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            R6ThrowableEntityUtils.EntityReboundOnBlock.reboundOrStop(result, this, 0.1d, ModSounds.FRAGGRENADE_TOUCH, 0.4d, world);
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
        super.onImpact(result);
    }

    @Override
    protected void updatePitchAndYaw() {

    }

    public void setRandomRotation() {
        this.rotationYaw = (float) (Math.random() * 360);
        this.rotationPitch = (float) (Math.random() * 360);
    }
}
