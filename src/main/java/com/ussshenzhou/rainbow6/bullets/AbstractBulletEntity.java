package com.ussshenzhou.rainbow6.bullets;

import com.ussshenzhou.rainbow6.bullets.penetrate.IsPenetrableBlocks;
import com.ussshenzhou.rainbow6.bullets.penetrate.PenetrateDamageCounter;
import com.ussshenzhou.rainbow6.entities.FragGrenadeEntity;
import com.ussshenzhou.rainbow6.particles.BulletHoleParticleData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Optional;

/**
 * @author USS_Shenzhou
 *
 * REMEMBER to register damage AND type!
 */

//better : extends ProjectileEntity
public abstract class AbstractBulletEntity extends ProjectileItemEntity {
    public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> type, World worldIn) {
        super(type, worldIn);
    }
    public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }


    protected static DataParameter<Integer> damage = EntityDataManager.createKey(AbstractBulletEntity.class, DataSerializers.VARINT);
    protected static DataParameter<Integer> penetrateCounter = EntityDataManager.createKey(AbstractBulletEntity.class,DataSerializers.VARINT);
    /**
     * ASR assault rifle
     * SMG sub-machine gun
     * LMG light machine gun
     * SHG shotgun
     * MMR marksman rifle
     * MAP machine pistol
     * HAG handgun
     */
    protected static DataParameter<String> type = EntityDataManager.createKey(AbstractBulletEntity.class,DataSerializers.STRING);

    public void setDamage(int dam){
        this.dataManager.set(damage,dam);
    }
    public int getDamage(){
        return this.dataManager.get(damage);
    }

    public void setPenetrateCounter(int p){
        this.dataManager.set(penetrateCounter,p);
    }
    public int getPenetrateCounter(){
        return  this.dataManager.get(penetrateCounter);
    }

    public void setType(String t){
        this.dataManager.set(type,t);
    }
    public String type(){
        return this.dataManager.get(type);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("damage",this.dataManager.get(damage));
        compound.putInt("penetratecounter",this.dataManager.get(penetrateCounter));
        compound.putString("type",this.dataManager.get(type));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("damage")){
            this.dataManager.set(damage,compound.getInt("damage"));
        }
        if (compound.contains("penetratecounter")){
            this.dataManager.set(penetrateCounter,compound.getInt("penetratecounter"));
        }
        if (compound.contains("type")){
            this.dataManager.set(type,compound.getString("type"));
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK){
            BlockRayTraceResult rayTraceResult = (BlockRayTraceResult)result;
            BlockPos pos = rayTraceResult.getPos();
            Block block = world.getBlockState(pos).getBlock();
            if (block.getExplosionResistance()>=4.2){
                this.impenetrable(result);
            }
            else{
                switch (IsPenetrableBlocks.isPenetrable(block)){
                    case 2:
                        this.impenetrable(result);
                        break;
                    case 1:
                        this.penetrate(result);
                        break;
                    case 0:
                        this.penetrate(result);
                        world.destroyBlock(pos,false);
                        world.removeTileEntity(pos);
                        break;
                    case -1:
                        this.penetrate(result);
                        int flag = PenetrateDamageCounter.call(this.dataManager.get(type),pos);
                        this.penetrateAndDestroy(flag,this.dataManager.get(type),pos,this.dataManager.get(damage));
                    case -2:
                        world.destroyBlock(pos,false);
                        world.removeTileEntity(pos);
                        //
                        break;
                    case -3:
                        //
                        break;
                    default:
                        this.penetrate(result);
                        break;

                }
            }
        }
        if (result.getType() == RayTraceResult.Type.ENTITY){

        }
    }

    private void impenetrable(RayTraceResult result){
        this.bulletHole(result);
        this.remove();
    }

    private void penetrate(RayTraceResult result){
        this.bulletHole(result);
        int i = this.dataManager.get(penetrateCounter);
        if (i == 0) {
            i = 1;
        } else {
            this.remove();
        }
        //One bullet can only penetrate once and then hit once.
        //R6S Chinese wiki: http://r6s.huijiwiki.com/wiki/%E6%9E%AA%E6%A2%B0%E7%A9%BF%E9%80%8F%E6%80%A7%E8%83%BD
    }

    private void bulletHole(RayTraceResult result){
        if (world.isRemote()){
            BlockRayTraceResult bResult = (BlockRayTraceResult)result;
            double X = result.getHitVec().getX() + 0.005 * bResult.getFace().getXOffset();
            double Y = result.getHitVec().getY() + 0.005 * bResult.getFace().getYOffset();
            double Z = result.getHitVec().getZ() + 0.005 * bResult.getFace().getZOffset();
            world.addParticle(new BulletHoleParticleData(bResult.getFace(),bResult.getPos()),false,X,Y,Z,0,0,0);
        }
    }

    private void penetrateAndDestroy(int flag,String type,BlockPos pos,int damage){
        //Tachanka?
        //Damage correct
        double correct;
        switch (type){
            case "SHG":
                correct = 25;
                break;
            case "MMR":
                correct = 3.555 * Math.pow(Math.E,0.0261 * damage);
                break;
            case "MAP":
            case "HAG":
                correct = 0.2699 * Math.pow(Math.E,0.0566 * damage);
                break;
            //ASR/SMG/LMG
            default:
                correct = 3.64;
                break;
        }
        if (flag * correct>=100){
            world.destroyBlock(pos,false);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(penetrateCounter,0);
    }
}
