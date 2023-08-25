package cn.ussshenzhou.rainbow6.gun.hit;

import net.minecraft.core.Direction;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author USS_Shenzhou modified from net.minecraft.client.model.*
 */
public final class ServerCubeDefinition {
   public final Vector3f origin;
   public final Vector3f dimensions;
   public final ServerCubeDeformation grow;
   public final boolean mirror;

   public ServerCubeDefinition(@Nullable String pComment, float pTexCoordU, float pTexCoordV, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, ServerCubeDeformation pGrow, boolean pMirror, float pTexScaleU, float pTexScaleV, Set<Direction> pVisibleFaces) {
      this.origin = new Vector3f(pOriginX, pOriginY, pOriginZ);
      this.dimensions = new Vector3f(pDimensionX, pDimensionY, pDimensionZ);
      this.grow = pGrow;
      this.mirror = pMirror;
   }

   public ServerCubeDefinition(Vector3f origin, Vector3f dimensions, ServerCubeDeformation grow, boolean mirror) {
      this.origin = origin;
      this.dimensions = dimensions;
      this.grow = grow;
      this.mirror = mirror;
   }
}