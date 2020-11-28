package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import com.ussshenzhou.rainbow6.capabilities.R6PlayerCapability;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.particles.GasSmokeParticleData;
import com.ussshenzhou.rainbow6.particles.ModParticleTypeRegistry;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
public class RemoteGasGrenadeEntity extends ProjectileItemEntity {
    public RemoteGasGrenadeEntity(EntityType<? extends RemoteGasGrenadeEntity> type, LivingEntity playerIn, World worldIn) {
        super(type, playerIn, worldIn);
    }
    public RemoteGasGrenadeEntity(EntityType<RemoteGasGrenadeEntity> remoteGasGrenadeEntityEntityType, World world) {
        super(remoteGasGrenadeEntityEntityType, world);
    }

    private Boolean exploded = false;
    private int i =0;
    @Override
    public void tick() {
        super.tick();
        if(this.onGround){
            this.setMotion(0,0,0);
        }
        if (this.exploded){
            if (!world.isRemote){
                if (i%20==0){
                    Predicate<PlayerEntity> ifexist = new Predicate<PlayerEntity>() {
                        @Override
                        public boolean test(PlayerEntity entity) {
                            return entity != null ;
                        }
                    };
                    List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(2.5d),ifexist);
                    if (!list.isEmpty()){
                        for(PlayerEntity playerEntity:list){
                            LazyOptional<IR6PlayerCapability> r6PlayerCap = playerEntity.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
                            IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(null);
                            if (!"smoke".equals(ir6PlayerCapability.getOperator())){
                                playerEntity.attackEntityFrom(new DamageSource("Remote Gas Grenade"),5);
                            }
                        }
                    }
                }
            }
            else if (i<150){
                world.addParticle(new GasSmokeParticleData(new Vector3d(0,0,0),new Color(0),0),false,this.getPosX()+rand(),this.getPosY()+rand(),this.getPosZ()+rand(),0,0,0);
            }
            i++;
        }
        if (i==200){
            this.remove();
        }
    }

    private double rand(){
        if (Math.random()*10<5){
            return Math.random();
        }
        else {
            return Math.random();
        }
    }

    @Override
    protected void onImpact(RayTraceResult raytraceResultIn) {
        if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            Vector3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY()-0.01, this.getPosZ());
            this.setMotion(vec3d);
            Vector3d vec3d1 = vec3d.normalize().scale((double) 0.05);
            this.prevPosX -= vec3d1.x;
            this.prevPosY -= vec3d1.y;
            this.prevPosZ -= vec3d1.z;
            this.playSound(ModSounds.NITRO_CELL_HIT, 0.4f, 1.0f);
            this.onGround = true;
            this.setNoGravity(true);
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
        }
    }

    public void explode(){
        if (!this.exploded){
            this.exploded=true;
            world.addParticle(ParticleTypes.CLOUD,this.getPosX(),this.getPosY(),this.getPosZ(),0,0,0);
            world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), ModSounds.REMOTEGASGRENADE_EXPLODE, SoundCategory.PLAYERS, 1.0f, 1.0f);

        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.remoteGasGrenadeItem;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public boolean hitByEntity(Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityIn;
            return this.attackEntityFrom(DamageSource.causePlayerDamage(playerentity), 1.0F);
        } else {
            return false;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote) {
            this.remove();
            this.markVelocityChanged();
            this.world.playSound((PlayerEntity)null,getPosition(), SoundEvents.BLOCK_SAND_FALL, SoundCategory.PLAYERS,1.0f,1.0f);
        }
        else {
            this.world.addParticle(ParticleTypes.CLOUD,this.getPosX(),this.getPosY(),this.getPosZ(),0,0,0);
        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.exploded = compound.getBoolean("exploded");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("exploded", this.exploded);
    }
}
