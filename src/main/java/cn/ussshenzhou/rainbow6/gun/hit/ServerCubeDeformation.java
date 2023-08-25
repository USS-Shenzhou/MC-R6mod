package cn.ussshenzhou.rainbow6.gun.hit;

/**
 * @author USS_Shenzhou modified from net.minecraft.client.model.*
 */
public class ServerCubeDeformation{
   public static final ServerCubeDeformation NONE = new ServerCubeDeformation(0.0F);
   final float growX;
   final float growY;
   final float growZ;

   public ServerCubeDeformation(float pGrowX, float pGrowY, float pGrowZ) {
      this.growX = pGrowX;
      this.growY = pGrowY;
      this.growZ = pGrowZ;
   }

   public ServerCubeDeformation(float pGrow) {
      this(pGrow, pGrow, pGrow);
   }

   public ServerCubeDeformation extend(float pGrow) {
      return new ServerCubeDeformation(this.growX + pGrow, this.growY + pGrow, this.growZ + pGrow);
   }

   public ServerCubeDeformation extend(float pGrowX, float pGrowY, float pGrowZ) {
      return new ServerCubeDeformation(this.growX + pGrowX, this.growY + pGrowY, this.growZ + pGrowZ);
   }

}