package cn.ussshenzhou.rainbow6.gun;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

public class PartDefinition {
   private final List<CubeDefinition> cubes;
   private final PartPose partPose;
   private final Map<String, PartDefinition> children = Maps.newHashMap();

   PartDefinition(List<CubeDefinition> pCubes, PartPose pPartPose) {
      this.cubes = pCubes;
      this.partPose = pPartPose;
   }

   public PartDefinition addOrReplaceChild(String pName, CubeListBuilder pCubes, PartPose pPartPose) {
      PartDefinition partdefinition = new PartDefinition(pCubes.getCubes(), pPartPose);
      PartDefinition partdefinition1 = this.children.put(pName, partdefinition);
      if (partdefinition1 != null) {
         partdefinition.children.putAll(partdefinition1.children);
      }

      return partdefinition;
   }

   public PartDefinition getChild(String pName) {
      return this.children.get(pName);
   }
}