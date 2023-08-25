package cn.ussshenzhou.rainbow6.gun.hit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.core.Direction;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author USS_Shenzhou modified from net.minecraft.client.model.*
 */
public class ServerCubeListBuilder {
   private static final Set<Direction> ALL_VISIBLE = EnumSet.allOf(Direction.class);
   private final List<ServerCubeDefinition> cubes = Lists.newArrayList();
   private int xTexOffs;
   private int yTexOffs;
   private boolean mirror;

   public ServerCubeListBuilder texOffs(int pXTexOffs, int pYTexOffs) {
      this.xTexOffs = pXTexOffs;
      this.yTexOffs = pYTexOffs;
      return this;
   }

   public ServerCubeListBuilder mirror() {
      return this.mirror(true);
   }

   public ServerCubeListBuilder mirror(boolean pMirror) {
      this.mirror = pMirror;
      return this;
   }

   public ServerCubeListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, ServerCubeDeformation pServerCubeDeformation, int pXTexOffs, int pYTexOffs) {
      this.texOffs(pXTexOffs, pYTexOffs);
      this.cubes.add(new ServerCubeDefinition(pComment, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, pServerCubeDeformation, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, int pXTexOffs, int pYTexOffs) {
      this.texOffs(pXTexOffs, pYTexOffs);
      this.cubes.add(new ServerCubeDefinition(pComment, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, ServerCubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
      this.cubes.add(new ServerCubeDefinition((String)null, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, ServerCubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, Set<Direction> pVisibleFaces) {
      this.cubes.add(new ServerCubeDefinition((String)null, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, ServerCubeDeformation.NONE, this.mirror, 1.0F, 1.0F, pVisibleFaces));
      return this;
   }

   public ServerCubeListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
      this.cubes.add(new ServerCubeDefinition(pComment, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, ServerCubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, ServerCubeDeformation pServerCubeDeformation) {
      this.cubes.add(new ServerCubeDefinition(pComment, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pServerCubeDeformation, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, boolean pMirror) {
      this.cubes.add(new ServerCubeDefinition((String)null, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, ServerCubeDeformation.NONE, pMirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, ServerCubeDeformation pServerCubeDeformation, float pTexScaleU, float pTexScaleV) {
      this.cubes.add(new ServerCubeDefinition((String)null, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pServerCubeDeformation, this.mirror, pTexScaleU, pTexScaleV, ALL_VISIBLE));
      return this;
   }

   public ServerCubeListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, ServerCubeDeformation pServerCubeDeformation) {
      this.cubes.add(new ServerCubeDefinition((String)null, (float)this.xTexOffs, (float)this.yTexOffs, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pServerCubeDeformation, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
      return this;
   }

   public List<ServerCubeDefinition> getCubes() {
      return ImmutableList.copyOf(this.cubes);
   }

   public static ServerCubeListBuilder create() {
      return new ServerCubeListBuilder();
   }
}