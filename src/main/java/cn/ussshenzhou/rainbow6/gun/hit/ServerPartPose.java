package cn.ussshenzhou.rainbow6.gun.hit;

/**
 * @author USS_Shenzhou modified from net.minecraft.client.model.*
 */
public class ServerPartPose {
   public static final ServerPartPose ZERO = offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   public final float x;
   public final float y;
   public final float z;
   public final float xRot;
   public final float yRot;
   public final float zRot;

   public ServerPartPose(float pX, float pY, float pZ, float pXRot, float pYRot, float pZRot) {
      this.x = pX;
      this.y = pY;
      this.z = pZ;
      this.xRot = pXRot;
      this.yRot = pYRot;
      this.zRot = pZRot;
   }

   public static ServerPartPose offset(float pX, float pY, float pZ) {
      return offsetAndRotation(pX, pY, pZ, 0.0F, 0.0F, 0.0F);
   }

   public static ServerPartPose rotation(float pXRot, float pYRot, float pZRot) {
      return offsetAndRotation(0.0F, 0.0F, 0.0F, pXRot, pYRot, pZRot);
   }

   public static ServerPartPose offsetAndRotation(float pX, float pY, float pZ, float pXRot, float pYRot, float pZRot) {
      return new ServerPartPose(pX, pY, pZ, pXRot, pYRot, pZRot);
   }
}