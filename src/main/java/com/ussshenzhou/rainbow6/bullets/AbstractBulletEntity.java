package com.ussshenzhou.rainbow6.bullets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * @author USS_Shenzhou
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
}
