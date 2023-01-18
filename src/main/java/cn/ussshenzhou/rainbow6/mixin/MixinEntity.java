package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author USS_Shenzhou
 */
@Mixin(value = Entity.class,remap = false)
public abstract class MixinEntity {

    /**
     * 0: exposed glowing. combining with vanilla flags' glowing(6).
     * 1:
     * 2:
     * 3:
     * 4:
     * 5:
     * 6:
     * 7:
     */
    @Unique
    private static final DataParameter<Byte> R6FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    @Final
    @Shadow
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);

    @Final
    @Shadow
    protected final EntityDataManager dataManager = this.getDataManager();

    @Inject(method = "Lnet/minecraft/entity/Entity;<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void r6Entity(EntityType<?> entityTypeIn, World worldIn,CallbackInfo ci) {
        this.dataManager.register(R6FLAGS, (byte) 0);
    }

    @Shadow
    public abstract EntityDataManager getDataManager();

    /*@Inject(method = "Lnet/minecraft/entity/Entity;<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void r6Entity(CallbackInfo ci) {
        this.dataManager.register(R6FLAGS, (byte) 0);
    }*/

    @Inject(method = "Lnet/minecraft/entity/Entity;getFlag(I)Z", at = @At("HEAD"), cancellable = true)
    private void r6GetFlag(int flag, CallbackInfoReturnable<Boolean> cir) {
        if (flag <= 7) {
            cir.setReturnValue((this.dataManager.get(FLAGS) & 1 << flag) != 0);
        } else if (flag <= 15) {
            cir.setReturnValue((this.dataManager.get(R6FLAGS) & 1 << (flag - 8)) != 0);
        }
    }


    @Inject(method = "Lnet/minecraft/entity/Entity;setFlag(IZ)V",at = @At("HEAD"),cancellable = true)
    private void r6SetFlag(int flag, boolean set,CallbackInfo ci) {
        if (flag <= 7) {
            byte b0 = this.dataManager.get(FLAGS);
            if (set) {
                this.dataManager.set(FLAGS, (byte) (b0 | 1 << flag));
            } else {
                this.dataManager.set(FLAGS, (byte) (b0 & ~(1 << flag)));
            }
        } else if (flag <= 15){
            byte b0 = this.dataManager.get(R6FLAGS);
            if (set) {
                this.dataManager.set(R6FLAGS, (byte) (b0 | 1 << (flag - 8)));
            } else {
                this.dataManager.set(R6FLAGS, (byte) (b0 & ~(1 << (flag - 8))));
            }
        }
    }
}
