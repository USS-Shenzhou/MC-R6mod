package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
/**
 * @author USS_Shenzhou
 */
public class ImpactGrenadeEntity extends ProjectileItemEntity {
    private float explosion = 1.3f;

    public ImpactGrenadeEntity(EntityType<? extends ImpactGrenadeEntity> type, LivingEntity playerIn, World worldIn) {
        super(type, playerIn, worldIn);
    }
    public ImpactGrenadeEntity(EntityType<ImpactGrenadeEntity> EntityType, World world) {
        super(EntityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.impactGrenade;
    }

    @Override
    protected void onImpact(RayTraceResult raytraceResultIn) {
        if (raytraceResultIn.getType() == RayTraceResult.Type.BLOCK) {
            this.setNoGravity(true);
            this.remove();
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceResultIn;
            if (world.getBlockState(blockraytraceresult.getPos()).getMaterial()== Material.WOOD){
                explosion=1.34f;
            }
            this.explode();
        }
        else if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY){
            this.setNoGravity(true);
            this.explode();
            this.remove();
            explosion=1.3f;
        }
    }
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void explode() {
        if (!this.world.isRemote) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), explosion, Explosion.Mode.DESTROY);
            this.remove();

        }
    }
    private double normalize(double x){
        return (Math.abs(x)+1)*0.5*x/Math.abs(x);
    }
}