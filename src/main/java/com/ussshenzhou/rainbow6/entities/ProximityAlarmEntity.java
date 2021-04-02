package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
public class ProximityAlarmEntity extends ProjectileItemEntity {

    public ProximityAlarmEntity(EntityType<? extends ProximityAlarmEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ProximityAlarmEntity(EntityType<? extends ProximityAlarmEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    public ProximityAlarmEntity(EntityType<? extends ProximityAlarmEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.proximityAlarmItem;
    }

    private int i=0;
    public int light = 15;
    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote){
            if (i%40==0){
                Predicate<PlayerEntity> ifexist = new Predicate<PlayerEntity>() {
                    @Override
                    public boolean test(PlayerEntity entity) {
                        return entity != null ;
                    }
                };
                List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(3d),ifexist);
                if (!list.isEmpty()){
                    for(PlayerEntity playerEntity:list){
                        LazyOptional<IR6PlayerCapability> r6PlayerCap = playerEntity.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
                        IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
                        if ("attacker".equals(ir6PlayerCapability.getR6Team()) && playerEntity.canEntityBeSeen(this)){
                            world.playSound(null,this.getPosition(),ModSounds.PROXIMITY_ALARM,SoundCategory.PLAYERS,0.7f,1.0f);
                        }
                    }
                }
            }
        }
        if(this.onGround){
            this.setMotion(0,0,0);
            this.light = 16 * world.getLightSubtracted(this.getPosition().offset(this.dataManager.get(direction)),0);
        }
        //LOGGER.warn(this.light);
        i++;
    }

    private static DataParameter<Direction> direction = EntityDataManager.createKey(ProximityAlarmEntity.class, DataSerializers.DIRECTION);
    public float X=this.dataManager.get(dX);
    public float Y=this.dataManager.get(dY);
    public float Z=this.dataManager.get(dZ);
    private static DataParameter<Float> dX = EntityDataManager.createKey(ProximityAlarmEntity.class,DataSerializers.FLOAT);
    private static DataParameter<Float> dY = EntityDataManager.createKey(ProximityAlarmEntity.class,DataSerializers.FLOAT);
    private static DataParameter<Float> dZ = EntityDataManager.createKey(ProximityAlarmEntity.class,DataSerializers.FLOAT);
    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) result;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            Vector3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY()-0.01, this.getPosZ());
            this.setMotion(vec3d);
            Vector3d vec3d1 = vec3d.normalize().scale((double) 0.05F);
            this.prevPosX -= vec3d1.x;
            this.prevPosY -= vec3d1.y;
            this.prevPosZ -= vec3d1.z;
            this.playSound(ModSounds.NITRO_CELL_HIT, 0.8f, 1.0f);
            this.onGround = true;
            this.setNoGravity(true);
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
            this.dataManager.set(direction,blockraytraceresult.getFace());
            Direction dir = blockraytraceresult.getFace();
            switch (dir){
                case UP:
                    X=180;
                    Y=(float) (Math.random()*360);
                    Z=0;
                    break;
                case DOWN:
                    X=0;
                    Y=(float) (Math.random()*360);
                    Z=0;
                    this.setPosition(this.getPosX(),this.getPosY()-0.01,this.getPosZ());
                    break;
                case NORTH:
                    X=90;
                    Y=0;
                    Z=(float) (Math.random()*360);
                    break;
                case SOUTH:
                    X=-90;
                    Y=0;
                    Z=(float) (Math.random()*360);
                    break;
                case EAST:
                    X=90;
                    Y=-90;
                    Z=(float) (Math.random()*360);
                    break;
                case WEST:
                    X=90;
                    Y=90;
                    Z=(float) (Math.random()*360);
                    break;
                default:
                    X=180;
                    Y=0;
                    Z=0;
                    break;
            }
            this.dataManager.set(dX,X);
            this.dataManager.set(dY,Y);
            this.dataManager.set(dZ,Z);
            if (this.getShooter() instanceof PlayerEntity){
                if ((!((PlayerEntity) this.getShooter()).canEntityBeSeen(this)) && this.getDistance(this.getShooter())<=5){
                    ItemStack returnStack = new ItemStack(ModItems.proximityAlarmItem);
                    ((PlayerEntity) this.getShooter()).addItemStackToInventory(returnStack);
                    this.remove();
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        switch (this.dataManager.get(direction)){
            case UP:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.175,0.075,0.175).offset(0,0.075,0);
            case DOWN:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.175,0.075,0.175).offset(0,-0.075,0);
            case NORTH:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.175,0.175,0.075).offset(0,0,-0.075);
            case SOUTH:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.175,0.175,0.075).offset(0,0,0.075);
            case EAST:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.075,0.175,0.175).offset(0.0750,0,0);
            case WEST:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.075,0.175,0.175).offset(-0.075,0,0);
            default:
                return new AxisAlignedBB(this.getPositionVec(),this.getPositionVec()).grow(0.175,0.175,0.175).offset(0,0,0);
        }
    }

    @Override
    protected void registerData() {
        this.dataManager.register(direction,Direction.UP);
        this.dataManager.register(dX,(float) (Math.random()*360));
        this.dataManager.register(dY,(float) (Math.random()*360));
        this.dataManager.register(dZ,(float) (Math.random()*360));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putString("direction",this.dataManager.get(direction).getString());
        compound.putFloat("dx",this.dataManager.get(dX));
        compound.putFloat("dy",this.dataManager.get(dY));
        compound.putFloat("dz",this.dataManager.get(dZ));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("direction")){
            this.dataManager.set(direction,Direction.byName(compound.getString("direction")));
        }
        if (compound.contains("dx")){
            this.dataManager.set(dX,compound.getFloat("dx"));
        }
        if (compound.contains("dy")){
            this.dataManager.set(dY,compound.getFloat("dy"));
        }
        if (compound.contains("dz")){
            this.dataManager.set(dZ,compound.getFloat("dz"));
        }
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
            this.markVelocityChanged();
            this.world.playSound((PlayerEntity)null,getPosition(),SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.PLAYERS,1.0f,1.0f);
            this.remove();
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
    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height*0.5f;
    }
}
