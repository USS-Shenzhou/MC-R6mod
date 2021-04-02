package com.ussshenzhou.rainbow6.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/**
 * @author USS_Shenzhou
 */
public class IronBarFloor extends Block {

    private static final VoxelShape NORTH_SOUTH_BAR1 = Block.makeCuboidShape(2.0d,14.0d,0.0d,3.0d,16.0d,16.0d);
    private static final VoxelShape NORTH_SOUTH_BAR2 = Block.makeCuboidShape(6.0d,14.0d,0.0d,7.0d,16.0d,16.0d);
    private static final VoxelShape NORTH_SOUTH_BAR3 = Block.makeCuboidShape(10.0d,14.0d,0.0d,11.0d,16.0d,16.0d);
    private static final VoxelShape NORTH_SOUTH_BAR4 = Block.makeCuboidShape(14.0d,14.0d,0.0d,15.0d,16.0d,16.0d);
    private static final VoxelShape NORTH_SOUTH = VoxelShapes.or(NORTH_SOUTH_BAR1,NORTH_SOUTH_BAR2,NORTH_SOUTH_BAR3,NORTH_SOUTH_BAR4);

    private static final VoxelShape EAST_WEST_BAR1 = Block.makeCuboidShape(0.0d,14.0d,2.0d,16.0d,16.0d,3.0d);
    private static final VoxelShape EAST_WEST_BAR2 = Block.makeCuboidShape(0.0d,14.0d,6.0d,16.0d,16.0d,7.0d);
    private static final VoxelShape EAST_WEST_BAR3 = Block.makeCuboidShape(0.0d,14.0d,10.0d,16.0d,16.0d,11.0d);
    private static final VoxelShape EAST_WEST_BAR4 = Block.makeCuboidShape(0.0d,14.0d,14.0d,16.0d,16.0d,15.0d);
    private static final VoxelShape EAST_WEST = VoxelShapes.or(EAST_WEST_BAR1,EAST_WEST_BAR2,EAST_WEST_BAR3,EAST_WEST_BAR4);


    public IronBarFloor() {
        super(Properties.create(Material.IRON)
        .hardnessAndResistance(100.0f,9.0f)
        .sound(SoundType.METAL)
        .notSolid()
        );
        this.setRegistryName("iron_bar_floor");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.FACING);
            switch (direction){
                case SOUTH:
                case NORTH:
                    return NORTH_SOUTH;
                case WEST:
                case EAST:
                    return EAST_WEST;
                default:
                    return VoxelShapes.empty();
            }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        super.fillStateContainer(builder);
    }
}
