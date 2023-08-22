package cn.ussshenzhou.rainbow6.gun;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;

public class CubeDeformation implements INBTSerializable<CompoundTag> {
   public static final CubeDeformation NONE = new CubeDeformation(0.0F);
   final float growX;
   final float growY;
   final float growZ;

   public CubeDeformation(float pGrowX, float pGrowY, float pGrowZ) {
      this.growX = pGrowX;
      this.growY = pGrowY;
      this.growZ = pGrowZ;
   }

   public CubeDeformation(float pGrow) {
      this(pGrow, pGrow, pGrow);
   }

   public CubeDeformation extend(float pGrow) {
      return new CubeDeformation(this.growX + pGrow, this.growY + pGrow, this.growZ + pGrow);
   }

   public CubeDeformation extend(float pGrowX, float pGrowY, float pGrowZ) {
      return new CubeDeformation(this.growX + pGrowX, this.growY + pGrowY, this.growZ + pGrowZ);
   }

   @Override
   public CompoundTag serializeNBT() {
      return null;
   }

   @Override
   public void deserializeNBT(CompoundTag nbt) {

   }
}