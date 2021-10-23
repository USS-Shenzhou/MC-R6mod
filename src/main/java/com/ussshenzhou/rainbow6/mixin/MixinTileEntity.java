package com.ussshenzhou.rainbow6.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author USS_Shenzhou
 */
@Mixin(TileEntity.class)
public abstract class MixinTileEntity {
    /**
     * @author USS_Shenzhou
     * extend tileentity render distance to entity distance.
     */
    @Inject(method = "Lnet/minecraft/tileentity/TileEntity;getMaxRenderDistanceSquared()D", at = @At("HEAD"), cancellable = true)
    public void r6GetMaxRenderDistanceSquared(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue((double) (Minecraft.getInstance().gameSettings.entityDistanceScaling * 160));
    }
}
