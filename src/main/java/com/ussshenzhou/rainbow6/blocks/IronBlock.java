package com.ussshenzhou.rainbow6.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
/**
 * @author USS_Shenzhou
 */
public class IronBlock extends Block {

    protected static final VoxelShape north = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape south = Block.makeCuboidShape(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 6.0D);
    protected static final VoxelShape west = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape east = Block.makeCuboidShape(10.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
    protected static final VoxelShape up = Block.makeCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    protected static final VoxelShape down = Block.makeCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);

    public IronBlock() {
        super(Properties.create(Material.IRON)
                .harvestLevel(3)
                .hardnessAndResistance(40.0f,6f)
        );
        this.setRegistryName("ironblock");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.FACING);
        switch(direction){
            case NORTH:
                return north;
            case SOUTH:
                return south;
            case EAST:
                return east;
            case WEST:
                return west;
            case UP:
                return up;
            case DOWN:
                return down;
            default: return north;
        }

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null){
            worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING,getFacingFromEntity(pos,placer)),2);

        }
    }
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity){
        Vector3d vec =entity.getPositionVec();
        return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()),(float) (vec.y - clickedBlock.getY()),(float) (vec.z - clickedBlock.getZ()));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
}
