package com.ussshenzhou.rainbow6.entities;


import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class NitroCellEntity extends ProjectileItemEntity {
    public NitroCellEntity(EntityType<? extends NitroCellEntity> type, LivingEntity playerIn, World worldIn) {
        super(type, playerIn, worldIn);
    }
    public NitroCellEntity(EntityType<NitroCellEntity> nitroCellEntityEntityType, World world) {
        super(nitroCellEntityEntityType, world);
    }

    @Nullable
    private BlockState inBlockState;
    @Override
    protected Item getDefaultItem() {
        return ModItems.nitroCell;
    }

    @Override
    protected void onImpact(RayTraceResult raytraceResultIn) {
        if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            this.inBlockState = blockstate;
            Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY()-0.01, this.getPosZ());
            this.setMotion(vec3d);
            Vec3d vec3d1 = vec3d.normalize().scale((double) 0.05F);
            this.prevPosX -= vec3d1.x;
            this.prevPosY -= vec3d1.y;
            this.prevPosZ -= vec3d1.z;
            this.playSound(ModSounds.NITRO_CELL_HIT, 0.8f, 1.0f);
            this.inGround = true;
            this.setNoGravity(true);
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(NitroCellEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(NitroCellEntity.class, DataSerializers.VARINT);
    private int fuseTime = 1;
    private float explosionRadius = 2.0f;
    private int timeSinceIgnited = 1;

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATE, -1);
        this.dataManager.register(IGNITED, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putShort("Fuse", (short) this.fuseTime);
        compound.putByte("ExplosionRadius", (byte) this.explosionRadius);
        compound.putBoolean("ignited", this.hasIgnited());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
    }

    public boolean hasIgnited() {
        return this.dataManager.get(IGNITED);
    }

    public void ignite() {
        this.dataManager.set(IGNITED, true);
    }

    public int getNitroCellState() {
        return this.dataManager.get(STATE);
    }

    public void setNitroCellState(int state) {
        this.dataManager.set(STATE, state);
    }

    public void explode() {
        if (!this.world.isRemote) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) this.explosionRadius, Explosion.Mode.DESTROY);
            this.remove();

        }
    }
    public void exploder() {
        this.ignite();
            this.setNitroCellState(1);
            int i = this.getNitroCellState();
            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
    }

}