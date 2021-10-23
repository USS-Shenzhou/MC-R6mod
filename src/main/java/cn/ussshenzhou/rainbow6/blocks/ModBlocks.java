package cn.ussshenzhou.rainbow6.blocks;

import net.minecraft.block.Block;

/**
 * @author USS_Shenzhou
 */
public class ModBlocks {
    public static Barricade barricade = new Barricade();
    public static IronBlock ironBlock = new IronBlock();
    public static Reinforcement reinforcement = new Reinforcement();
    public static BlackMirror blackMirror = new BlackMirror();
    public static IronBarFloor ironBarFloor = new IronBarFloor();

    public static Block oakPlanksFloor = new PlanksFloor().setRegistryName("oak_planks_floor");
    public static Block sprucePlanksFloor = new PlanksFloor().setRegistryName("spruce_planks_floor");
    public static Block birchPlanksFloor = new PlanksFloor().setRegistryName("birch_planks_floor");
    public static Block junglePlanksFloor = new PlanksFloor().setRegistryName("jungle_planks_floor");
    public static Block acaciaPlanksFloor =  new PlanksFloor().setRegistryName("acacia_planks_floor");
    public static Block darkOakPlanksFloor = new PlanksFloor().setRegistryName("dark_oak_planks_floor");
    public static Block crimsonPlanksFloor = new PlanksFloor().setRegistryName("crimson_planks_floor");
    public static Block warpedPlanksFloor = new PlanksFloor().setRegistryName("warped_planks_floor");

    public static Block oakHatch = new Hatch().setRegistryName("oak_hatch");
    public static Block spruceHatch = new Hatch().setRegistryName("spruce_hatch");
    public static Block birchHatch = new Hatch().setRegistryName("birch_hatch");
    public static Block jungleHatch = new Hatch().setRegistryName("jungle_hatch");
    public static Block acaciaHatch =  new Hatch().setRegistryName("acacia_hatch");
    public static Block darkoakHatch = new Hatch().setRegistryName("dark_oak_hatch");
    public static Block crimsonHatch = new Hatch().setRegistryName("crimson_hatch");
    public static Block warpedHatch = new Hatch().setRegistryName("warped_hatch");
    public static Block brokenHatch = new BrokenHatch().setRegistryName("broken_hatch");
}
