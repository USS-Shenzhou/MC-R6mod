package cn.ussshenzhou.rainbow6.gun.entity;

import cn.ussshenzhou.rainbow6.gun.data.Modifier;
import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
public class TestBulletEntity extends Entity {
    protected LivingEntity shooter;
    protected TestGun gun;
    protected Modifier gunModifier;

    public static int lifeTime = 10;
    public static int speed = 15;

    public TestBulletEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public TestBulletEntity(Level pLevel, LivingEntity shooter, TestGun gun, Modifier gunModifier) {
        this(ModGunEntityTypeRegistry.BULLET_ENTITY_TYPE.get(), pLevel);
        this.shooter = shooter;
        this.gun = gun;
        this.gunModifier = gunModifier;
        initPosAndSpeed();
    }

    private void initPosAndSpeed() {
        var pos = shooter.getEyePosition();
        this.setPos(pos);
        var speedVec3 = shooter.getLookAngle().multiply(speed, speed, speed);
        this.setDeltaMovement(speedVec3);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            clientTick();
        }
        //collision
        var hit = ProjectileUtil.getHitResultOnMoveVector(this, e -> !(e instanceof TestBulletEntity));
        switch (hit.getType()) {
            case MISS -> {
            }
            case BLOCK -> {

            }
            case ENTITY -> {

            }
        }
        //move
        this.setPos(this.getPosition(1).add(this.getDeltaMovement()));
        //life
        if (this.tickCount >= lifeTime) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void clientTick() {

    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
