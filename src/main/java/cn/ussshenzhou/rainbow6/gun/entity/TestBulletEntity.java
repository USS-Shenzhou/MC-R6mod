package cn.ussshenzhou.rainbow6.gun.entity;

import cn.ussshenzhou.rainbow6.gun.data.Modifier;
import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import cn.ussshenzhou.rainbow6.util.ModDamageSources;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * @author USS_Shenzhou
 */
public class TestBulletEntity extends Entity {
    protected LivingEntity shooter;
    protected TestGun gun;
    protected Modifier gunModifier;
    protected int hitCount = 0;
    protected Vec3 spawnPos;

    public static int lifeTime = 6;
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
        this.spawnPos = pos;
        var speedVec3 = shooter.getLookAngle().multiply(speed, speed, speed);
        this.setDeltaMovement(speedVec3);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            clientTick();
        }
        checkHit(null);
        //move
        this.setPos(this.getPosition(1).add(this.getDeltaMovement()));
        //life
        if (this.tickCount >= lifeTime) {
            this.remove();
        }
    }

    protected void checkHit(@Nullable Object additionalIgnore) {
        var h = ProjectileUtil.getHitResultOnMoveVector(this, e -> (!(e instanceof TestBulletEntity)) && e != additionalIgnore);
        switch (h.getType()) {
            case MISS -> {
            }
            case BLOCK -> {
                var penetrated = true;
                if (penetrated) {
                    hitCount++;
                    penetrateAfterHit(h);
                } else {
                    this.remove();
                }
                //TODO if not inside: hit-block effect
            }
            case ENTITY -> {
                hitCount++;
                var hit = (EntityHitResult) h;
                var pos = hit.getLocation();
                var distance = spawnPos.distanceTo(pos);
                var damage = gunModifier.getDamage(gun.getFixedProperty(), distance);
                Entity victim = hit.getEntity();
                hit.getEntity().hurt(ModDamageSources.shot(this.shooter, victim), damage);
                //TODO head shot
                penetrateAfterHit(h);
            }
        }
    }

    protected void penetrateAfterHit(HitResult prevHit) {
        if (hitCount > 1) {
            this.remove();
            return;
        }
        if (prevHit instanceof EntityHitResult entityHitResult) {
            checkHit(entityHitResult.getEntity());
        } else {
            var move = this.getDeltaMovement();
            this.setDeltaMovement(move.multiply(0.8, 0.8, 0.8));
            //jump current block
            @SuppressWarnings("SuspiciousNameCombination")
            //FIXME This way has a flaw, but acceptable. Use more math to fix it.
            double max = 1 / Mth.absMax(move.x, Mth.absMax(move.y, move.z));
            move = move.multiply(max, max, max).multiply(1.001, 1.001, 1.001);
            this.setPos(prevHit.getLocation().add(move));
            checkHit(null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void clientTick() {

    }

    protected void remove() {
        this.remove(RemovalReason.DISCARDED);
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
