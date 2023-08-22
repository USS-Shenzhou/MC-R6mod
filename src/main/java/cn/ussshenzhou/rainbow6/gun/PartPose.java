package cn.ussshenzhou.rainbow6.gun;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PartPose {
   public static final PartPose ZERO = offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   public final float x;
   public final float y;
   public final float z;
   public final float xRot;
   public final float yRot;
   public final float zRot;

   private PartPose(float pX, float pY, float pZ, float pXRot, float pYRot, float pZRot) {
      this.x = pX;
      this.y = pY;
      this.z = pZ;
      this.xRot = pXRot;
      this.yRot = pYRot;
      this.zRot = pZRot;
   }

   public static PartPose offset(float pX, float pY, float pZ) {
      return offsetAndRotation(pX, pY, pZ, 0.0F, 0.0F, 0.0F);
   }

   public static PartPose rotation(float pXRot, float pYRot, float pZRot) {
      return offsetAndRotation(0.0F, 0.0F, 0.0F, pXRot, pYRot, pZRot);
   }

   public static PartPose offsetAndRotation(float pX, float pY, float pZ, float pXRot, float pYRot, float pZRot) {
      return new PartPose(pX, pY, pZ, pXRot, pYRot, pZRot);
   }
}