package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class ImpactGrenadeEntity extends ProjectileItemEntity {
    private float explosion = 1.2f;

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
            String isPlanks = "planks";
            BlockPos pos = this.getPosition().add(normalize(this.getMotion().getX()),normalize(this.getMotion().getY()),normalize(this.getMotion().getZ()));
            LOGGER.info(world.getBlockState(pos).getBlock());
            if (world.getBlockState(pos).getBlock().toString().contains(isPlanks)){
                explosion=1.36f;
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
