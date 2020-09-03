package com.ussshenzhou.rainbow6.blocks;

import com.ussshenzhou.rainbow6.tileentities.ReinforcementTileEntity;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Reinforcement extends Block{
    protected static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 0.0D);
    protected static final VoxelShape WEST = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    protected static final VoxelShape UP = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape DOWN = Block.makeCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static BooleanProperty upper = BooleanProperty.create("upper");


    public Reinforcement() {
        super(Properties.create(Material.IRON)
                .harvestLevel(0)
                .hardnessAndResistance(40.0f,7.0f)
                .sound(new SoundType(1.0F,1.0F, ModSounds.MUTE, SoundEvents.BLOCK_METAL_STEP, ModSounds.REINFORCEMENT_PLACE, SoundEvents.BLOCK_METAL_HIT,ModSounds.MUTE))
                .notSolid()
        );
        this.setRegistryName("reinforcement");
        this.setDefaultState(this.stateContainer.getBaseState().with(upper, true));
    }




    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.FACING);
        switch(direction){
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            default:
                return NORTH;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null){
            worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING,getFacingFromEntity(pos,placer)).with(upper,false),2);
            worldIn.setTileEntity(pos,new ReinforcementTileEntity());
        }
    }
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity){
        Vec3d vec =entity.getPositionVec();
        return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()),(float) (vec.y - clickedBlock.getY()),(float) (vec.z - clickedBlock.getZ()));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        builder.add(upper);
        super.fillStateContainer(builder);
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ReinforcementTileEntity();
    }

    /*@Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().getItem() == ModItems.blackMirror ? true : super.isReplaceable(state, useContext);
    }*/
}
