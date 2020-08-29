package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ImpactGrenadeEntity extends ProjectileItemEntity {
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
            this.explode();
            this.remove();
        }
        else if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY){
            this.setNoGravity(true);
            this.explode();
            this.remove();
        }
    }
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void explode() {
        if (!this.world.isRemote) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 1.3f, Explosion.Mode.DESTROY);
            this.remove();

        }
    }
}
