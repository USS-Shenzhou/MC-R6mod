package cn.ussshenzhou.rainbow6.gun;

import net.minecraft.core.Direction;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Set;

public final class CubeDefinition {
   @Nullable
   private final String comment;
   private final Vector3f origin;
   private final Vector3f dimensions;
   private final CubeDeformation grow;
   private final boolean mirror;
   private final Set<Direction> visibleFaces;

   public CubeDefinition(@Nullable String pComment, float pTexCoordU, float pTexCoordV, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation pGrow, boolean pMirror, float pTexScaleU, float pTexScaleV, Set<Direction> pVisibleFaces) {
      this.comment = pComment;
      this.origin = new Vector3f(pOriginX, pOriginY, pOriginZ);
      this.dimensions = new Vector3f(pDimensionX, pDimensionY, pDimensionZ);
      this.grow = pGrow;
      this.mirror = pMirror;
      this.visibleFaces = pVisibleFaces;
   }
}