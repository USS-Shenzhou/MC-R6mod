package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.mixinproxy.FoodDataProxy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author USS_Shenzhou
 */
@Mixin(FoodData.class)
public class FoodDataMixin implements FoodDataProxy {
    @Shadow
    private int foodLevel;
    @Shadow
    private float saturationLevel;
    @Shadow
    private float exhaustionLevel;
    @Shadow
    private int tickTimer;

    boolean r6msFoodEnabled = true;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void r6msDisableFoodInMatch(Player pPlayer, CallbackInfo ci) {
        if (!r6msFoodEnabled) {
            ci.cancel();
        }
    }

    @Override
    public boolean isR6msFoodEnabled() {
        return r6msFoodEnabled;
    }

    @Override
    public void setR6msFoodEnabled(boolean r6msFoodEnabled) {
        this.r6msFoodEnabled = r6msFoodEnabled;
        if (!r6msFoodEnabled) {
            foodLevel = 20;
            saturationLevel = 20;
            exhaustionLevel = 0;
            tickTimer = 0;
        }
    }

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void r6msDisableExhaustionInMatch(float pExhaustion, CallbackInfo ci) {
        if (!r6msFoodEnabled) {
            ci.cancel();
        }
    }
}
