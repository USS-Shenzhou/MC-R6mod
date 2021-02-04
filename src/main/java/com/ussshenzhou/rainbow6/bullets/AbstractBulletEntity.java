package com.ussshenzhou.rainbow6.bullets;

import com.ussshenzhou.rainbow6.bullets.penetrate.IsPenetrableBlocks;
import com.ussshenzhou.rainbow6.bullets.penetrate.PenetrateDamageCounter;
import com.ussshenzhou.rainbow6.entities.FragGrenadeEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * @author USS_Shenzhou
 */

//better : extends ProjectileEntity
public class AbstractBulletEntity extends ProjectileItemEntity {
    public AbstractBulletEntity(EntityType<AbstractBulletEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public AbstractBulletEntity(EntityType<AbstractBulletEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    public AbstractBulletEntity(EntityType<AbstractBulletEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }

    private static DataParameter<Integer> damage = EntityDataManager.createKey(AbstractBulletEntity.class, DataSerializers.VARINT);


    public void setDamage(int dam){
        this.dataManager.set(damage,dam);
    }
    public int getDamage(){
        return this.dataManager.get(damage);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("damage",this.dataManager.get(damage));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("damage")){
            this.dataManager.set(damage,compound.getInt("damage"));
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType()== RayTraceResult.Type.BLOCK){
            BlockRayTraceResult rayTraceResult = (BlockRayTraceResult)result;
            BlockPos pos = rayTraceResult.getPos();
            Block block = world.getBlockState(pos).getBlock();
            if (block.getExplosionResistance()>=4.2){
                this.impenetrable();
            }
            else{
                switch (IsPenetrableBlocks.isPenetrable(block)){
                    case 2:
                        this.impenetrable();
                        break;
                    case 1:
                        this.penetrate();
                        break;
                    case 0:
                        this.penetrate();
                        world.destroyBlock(pos,false);
                        world.removeTileEntity(pos);
                        break;
                    case -1:
                        int flag = PenetrateDamageCounter.call(pos);
                        //
                        break;
                    case -2:
                        world.destroyBlock(pos,false);
                        world.removeTileEntity(pos);
                        //
                        break;
                    case -3:
                        //
                        break;
                    default:
                        this.penetrate();
                        break;

                }
            }
        }
    }

    private void impenetrable(){

    }

    private void penetrate(){

    }

    //remove
    @Override
    protected Item getDefaultItem() {
        return null;
    }
}
