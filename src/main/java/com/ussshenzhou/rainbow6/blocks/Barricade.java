package com.ussshenzhou.rainbow6.blocks;

import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
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
public class Barricade extends Block {

    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final BooleanProperty WINDOW = BooleanProperty.create("window");
    public static final DirectionProperty DIRECTION = BlockStateProperties.FACING;

    protected static final VoxelShape BOTTOM_NORTH = Block.makeCuboidShape(0.0D,2.0D,-1.0D,16.0D,16.0D,0.0D);
    protected static final VoxelShape BOTTOM_SOUTH = Block.makeCuboidShape(0.0D,2.0D,16.0D,16.0D,16.0D,17.0D);
    protected static final VoxelShape BOTTOM_WEST = Block.makeCuboidShape(-1.0D,2.0D,0.0D,0.0D,16.0D,16.0D);
    protected static final VoxelShape BOTTOM_EAST = Block.makeCuboidShape(16.0D,2.0D,0.0D,17.0D,16.0D,16.0D);

    protected static final VoxelShape TOP_NORTH = Block.makeCuboidShape(0.0D,0.0D,-1.0D,16.0D,17.0D,0.0D);
    protected static final VoxelShape TOP_SOUTH = Block.makeCuboidShape(0.0D,0.0D,16.0D,16.0D,17.0D,17.0D);
    protected static final VoxelShape TOP_WEST = Block.makeCuboidShape(-1.0D,0.0D,0.0D,0.0D,17.0D,16.0D);
    protected static final VoxelShape TOP_EAST = Block.makeCuboidShape(16.0D,0.0D,0.0D,17.0D,17.0D,16.0D);

    protected static final VoxelShape WINDOW_BOTTOM_NORTH = Block.makeCuboidShape(0.0D,-5.0D,15.0D,16.0D,16.0D,16.0D);
    protected static final VoxelShape WINDOW_BOTTOM_SOUTH = Block.makeCuboidShape(0.0D,-5.0D,0.0D,16.0D,16.0D,1.0D);
    protected static final VoxelShape WINDOW_BOTTOM_WEST = Block.makeCuboidShape(15.0D,-5.0D,0.0D,16.0D,16.0D,16.0D);
    protected static final VoxelShape WINDOW_BOTTOM_EAST = Block.makeCuboidShape(0.0D,-5.0D,0.0D,1.0D,16.0D,16.0D);

    protected static final VoxelShape WINDOW_TOP_NORTH = Block.makeCuboidShape(0.0D,0.0D,15.0D,16.0D,17.0D,16.0D);
    protected static final VoxelShape WINDOW_TOP_SOUTH = Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,17.0D,1.0D);
    protected static final VoxelShape WINDOW_TOP_WEST = Block.makeCuboidShape(15.0D,0.0D,0.0D,16.0D,17.0D,16.0D);
    protected static final VoxelShape WINDOW_TOP_EAST = Block.makeCuboidShape(0.0D,0.0D,0.0D,1.0D,17.0D,16.0D);

    public Barricade(){
        super(Properties.create(Material.WOOD)
        .hardnessAndResistance(0.3f,0.5f)
        .sound(new SoundType(1.0f,1.0f, ModSounds.BARRICADE_BREAK, SoundEvents.BLOCK_WOOD_STEP,null,SoundEvents.BLOCK_WOOD_HIT,SoundEvents.BLOCK_WOOD_FALL))
        .notSolid()
        );
        this.setRegistryName("barricade");
        this.setDefaultState(this.stateContainer.getBaseState().with(BOTTOM,true).with(WINDOW,false).with(DIRECTION,Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(DIRECTION);
        if (state.get(WINDOW)){
            if (state.get(BOTTOM)) {
                switch (direction) {
                    case NORTH:
                        return WINDOW_BOTTOM_NORTH;
                    case SOUTH:
                        return WINDOW_BOTTOM_SOUTH;
                    case EAST:
                        return WINDOW_BOTTOM_EAST;
                    case WEST:
                        return WINDOW_BOTTOM_WEST;
                    default:
                        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
                }
            }
            else {
                switch (direction){
                    case NORTH:
                        return WINDOW_TOP_NORTH;
                    case SOUTH:
                        return WINDOW_TOP_SOUTH;
                    case EAST:
                        return WINDOW_TOP_EAST;
                    case WEST:
                        return WINDOW_TOP_WEST;
                    default:
                        return Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,16.0D,16.0D);
                }
            }
        }
        else {
            if (state.get(BOTTOM)) {
                switch (direction) {
                    case NORTH:
                        return BOTTOM_NORTH;
                    case SOUTH:
                        return BOTTOM_SOUTH;
                    case EAST:
                        return BOTTOM_EAST;
                    case WEST:
                        return BOTTOM_WEST;
                    default:
                        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
                }
            }
            else {
                switch (direction){
                    case NORTH:
                        return TOP_NORTH;
                    case SOUTH:
                        return TOP_SOUTH;
                    case EAST:
                        return TOP_EAST;
                    case WEST:
                        return TOP_WEST;
                    default:
                        return Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,16.0D,16.0D);
                }
            }
        }
    }

    public void cancelPlace(PlayerEntity player){
        ItemStack returnStack = new ItemStack(ModItems.barricadeItem);
        player.addItemStackToInventory(returnStack);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer instanceof PlayerEntity){
            Direction direction = getFacingFromEntity(pos,placer);
            //cancel the original one
            worldIn.setBlockState(pos,Blocks.AIR.getDefaultState());
            //invalid up-or-down direction
            if (direction == Direction.UP||direction == Direction.DOWN){
                cancelPlace((PlayerEntity)placer);
            }
            else{
                //a window
                if (worldIn.getBlockState(pos.offset(direction,1).add(0,-1,0)).getBlock() instanceof AirBlock||worldIn.getBlockState(pos.add(0,-1,0)).getBlock() instanceof AirBlock){
                    //a window without glass
                    if (!(worldIn.getBlockState(pos.offset(direction.getOpposite(),1)).getBlock() instanceof PaneBlock)){
                        //move pos to the right X/Z-place for barricade
                        pos=pos.offset(direction,1);
                    }
                    //place top part first
                    //pos at bottom
                    if (worldIn.getBlockState(pos.offset(direction.getOpposite(),1).add(0,1,0)).getBlock() instanceof AirBlock||worldIn.getBlockState(pos.offset(direction.getOpposite(),1).add(0,1,0)).getBlock() instanceof PaneBlock){
                        //move pos to the right Y-place for barricade
                        pos=pos.add(0,1,0);
                    }
                    //only can be placed on a window correctly
                    if (worldIn.getBlockState(pos).getBlock() instanceof AirBlock && worldIn.getBlockState(pos.add(0,-1,0)).getBlock() instanceof AirBlock){
                        worldIn.setBlockState(pos,state.with(BOTTOM,false).with(WINDOW,true).with(DIRECTION,direction));
                        worldIn.setBlockState(pos.add(0,-1,0),state.with(BOTTOM,true).with(WINDOW,true).with(DIRECTION,direction));
                        worldIn.playSound(pos.getX(),pos.getY()-0.5,pos.getZ(),ModSounds.BARRICADE_PLACE, SoundCategory.PLAYERS,1.0f,1.0f,true);
                    }
                    else {
                        cancelPlace((PlayerEntity)placer);
                    }
                }
                //a door
                else {
                    //place top part first
                    //pos at bottom
                    if (worldIn.getBlockState(pos.offset(direction.getOpposite(),1).add(0,1,0)).getBlock() instanceof AirBlock){
                        //move pos to the right Y-place for barricade
                        pos=pos.add(0,1,0);
                    }
                    //only can be placed on a door correctly
                    if (worldIn.getBlockState(pos).getBlock() instanceof AirBlock && worldIn.getBlockState(pos.add(0,-1,0)).getBlock() instanceof AirBlock){
                        worldIn.setBlockState(pos,state.with(BOTTOM,false).with(WINDOW,false).with(DIRECTION,direction));
                        worldIn.setBlockState(pos.add(0,-1,0),state.with(BOTTOM,true).with(WINDOW,false).with(DIRECTION,direction));
                        worldIn.playSound(pos.getX(),pos.getY()-0.5,pos.getZ(),ModSounds.BARRICADE_PLACE, SoundCategory.PLAYERS,1.0f,1.0f,true);
                    }
                    else {
                        cancelPlace((PlayerEntity)placer);
                    }
                }
            }
        }
    }
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity){
        Vector3d vec =entity.getPositionVec();
        return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()),(float) (vec.y - clickedBlock.getY()),(float) (vec.z - clickedBlock.getZ()));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BOTTOM);
        builder.add(DIRECTION);
        builder.add(WINDOW);
        super.fillStateContainer(builder);
    }


}