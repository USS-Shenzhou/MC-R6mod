package cn.ussshenzhou.rainbow6.blocks;

import cn.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

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
    public static Block acaciaPlanksFloor = new PlanksFloor().setRegistryName("acacia_planks_floor");
    public static Block darkOakPlanksFloor = new PlanksFloor().setRegistryName("dark_oak_planks_floor");
    public static Block crimsonPlanksFloor = new PlanksFloor().setRegistryName("crimson_planks_floor");
    public static Block warpedPlanksFloor = new PlanksFloor().setRegistryName("warped_planks_floor");

    public static Block oakHatch = new Hatch().setRegistryName("oak_hatch");
    public static Block spruceHatch = new Hatch().setRegistryName("spruce_hatch");
    public static Block birchHatch = new Hatch().setRegistryName("birch_hatch");
    public static Block jungleHatch = new Hatch().setRegistryName("jungle_hatch");
    public static Block acaciaHatch = new Hatch().setRegistryName("acacia_hatch");
    public static Block darkoakHatch = new Hatch().setRegistryName("dark_oak_hatch");
    public static Block crimsonHatch = new Hatch().setRegistryName("crimson_hatch");
    public static Block warpedHatch = new Hatch().setRegistryName("warped_hatch");
    public static Block brokenHatch = new BrokenHatch().setRegistryName("broken_hatch");

    public static Block barricadeBlock = new Block(AbstractBlock.Properties
            .create(Material.WOOD)
            .hardnessAndResistance(0.3f, 0.5f)
            .sound(new SoundType(1.0f, 1.0f, ModSounds.BARRICADE_BREAK, SoundEvents.BLOCK_WOOD_STEP, null, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL))
    ).setRegistryName("barricade_block");


    static final VoxelShape shape = Block.makeCuboidShape(0, 2, 0, 16, 16, 16);
    public static Block barricadeBlockSlit = new Block(AbstractBlock.Properties
            .create(Material.WOOD)
            .hardnessAndResistance(0.3f, 0.5f)
            .sound(new SoundType(1.0f, 1.0f, ModSounds.BARRICADE_BREAK, SoundEvents.BLOCK_WOOD_STEP, null, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL))
            .notSolid()) {

        @Override
        public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return shape;
        }
    }
            .setRegistryName("barricade_block_slit");
}
