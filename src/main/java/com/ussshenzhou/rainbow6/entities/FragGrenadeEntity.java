package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.lwjgl.system.CallbackI;

public class FragGrenadeEntity extends ProjectileItemEntity {

    public FragGrenadeEntity(EntityType<FragGrenadeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public FragGrenadeEntity(EntityType<? extends FragGrenadeEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
    }


    private int timeCountDown = 90;
    @Override
    protected Item getDefaultItem() {
        return ModItems.fragGrenade;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK){
            Vector3d oldMotion = this.getMotion();
            double velocity = Math.sqrt(Math.pow(oldMotion.x,2)+Math.pow(oldMotion.y,2)+Math.pow(oldMotion.z,2));
            if (velocity<=0.1) {
                this.setVelocity(0,0,0);
            }
            else {
                Vector3d newMotion = this.impactReflection(result);
                this.setMotion(newMotion);
                world.playSound((PlayerEntity)null,getPosition(),ModSounds.FRAGGRENADE_TOUCH,SoundCategory.PLAYERS,1.0f,1.0f);
            }
            this.markVelocityChanged();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult)result;
            Entity entity = entityRayTraceResult.getEntity();
            if (entity!=this.func_234616_v_()){
                this.setVelocity(0,0,0);
                this.markVelocityChanged();
            }
        }
    }

    @Override
    public void tick() {
        if (timeCountDown<=0){
            this.explode();
            this.remove();
        }
        else {
            timeCountDown--;
        }
        super.tick();
    }


    public void explode() {
        if (!this.world.isRemote) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 2f, Explosion.Mode.DESTROY);
            this.remove();
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("timeCountDown",timeCountDown);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("timeCountDown")){
            this.timeCountDown = compound.getInt("timeCountDown");
        }
    }

    public void setTimeCountDown(int timeCountDown){
        this.timeCountDown = timeCountDown;
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
        return false;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public Vector3d impactReflection(RayTraceResult oldResult){
        Vector3d oldMotion = this.getMotion();
        Vector3d newMotion = oldMotion;
        double reduction =0.6;
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) oldResult;
        Direction direction = blockraytraceresult.getFace();
        switch (direction){
            case NORTH:
            case SOUTH:
                newMotion = oldMotion.mul(reduction,reduction,-reduction);
                break;
            case EAST:
            case WEST:
                newMotion = oldMotion.mul(-reduction,reduction,reduction);
                break;
            case UP:
            case DOWN:
                newMotion = oldMotion.mul(reduction,-reduction,reduction);
                break;
            default:
                break;
        }
        return newMotion;
    }
}
