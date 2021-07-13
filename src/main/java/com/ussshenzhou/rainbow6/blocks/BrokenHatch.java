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
public class BrokenHatch extends Block {

    private static final VoxelShape SOUTH1 = Block.makeCuboidShape(0,0,0,1,16,16);
    private static final VoxelShape SOUTH2 = Block.makeCuboidShape(0,0,0,16,16,1);
    private static final VoxelShape SOUTH = VoxelShapes.or(SOUTH1,SOUTH2);

    private static final VoxelShape NORTH1 = Block.makeCuboidShape(15,0,0,16,16,16);
    private static final VoxelShape NORTH2 = Block.makeCuboidShape(0,0,15,15,16,16);
    private static final VoxelShape NORTH = VoxelShapes.or(NORTH1,NORTH2);

    private static final VoxelShape EAST1 = Block.makeCuboidShape(0,0,15,16,16,16);
    private static final VoxelShape EAST2 = Block.makeCuboidShape(0,0,0,1,16,15);
    private static final VoxelShape EAST = VoxelShapes.or(EAST1,EAST2);

    private static final VoxelShape WEST1 = Block.makeCuboidShape(0,0,0,16,16,1);
    private static final VoxelShape WEST2 = Block.makeCuboidShape(15,0,0,16,16,15);
    private static final VoxelShape WEST = VoxelShapes.or(WEST1,WEST2);

    public BrokenHatch() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(100,9)
                .sound(SoundType.METAL)
                .notSolid()
        );
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.FACING);
        switch (direction){
            case SOUTH:
                return SOUTH;
            case NORTH:
                return NORTH;
            case WEST:
                return WEST;
            case EAST:
                return EAST;
            default:
                return VoxelShapes.fullCube();
        }
    }
}
