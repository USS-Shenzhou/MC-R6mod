package com.ussshenzhou.rainbow6.blocks;

import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author USS_Shenzhou
 */
public class Hatch extends Block {

    private static final VoxelShape WOOD = Block.makeCuboidShape(0,1,0,16,15,16);

    private static final VoxelShape SOUTH1 = Block.makeCuboidShape(0,0,0,1,16,16);
    private static final VoxelShape SOUTH2 = Block.makeCuboidShape(0,0,0,16,16,1);
    private static final VoxelShape SOUTH = VoxelShapes.or(SOUTH1,SOUTH2,WOOD);

    private static final VoxelShape NORTH1 = Block.makeCuboidShape(15,0,0,16,16,16);
    private static final VoxelShape NORTH2 = Block.makeCuboidShape(0,0,15,15,16,16);
    private static final VoxelShape NORTH = VoxelShapes.or(NORTH1,NORTH2,WOOD);

    private static final VoxelShape EAST1 = Block.makeCuboidShape(0,0,15,16,16,16);
    private static final VoxelShape EAST2 = Block.makeCuboidShape(0,0,0,1,16,15);
    private static final VoxelShape EAST = VoxelShapes.or(EAST1,EAST2,WOOD);

    private static final VoxelShape WEST1 = Block.makeCuboidShape(0,0,0,16,16,1);
    private static final VoxelShape WEST2 = Block.makeCuboidShape(15,0,0,16,16,16);
    private static final VoxelShape WEST = VoxelShapes.or(WEST1,WEST2,WOOD);

    public Hatch() {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(80,0.8f)
                .sound(SoundType.WOOD)
                .notSolid()
        );
    }

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0,2,0,16,16,16);

    private void onDestroyed(BlockState state, World worldIn, BlockPos pos){
        worldIn.setBlockState(pos,ModBlocks.brokenHatch.getDefaultState().with(BlockStateProperties.FACING,state.get(BlockStateProperties.FACING)));
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
            this.onDestroyed(state,(World)worldIn,pos);
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        this.onDestroyed(state,world,pos);
    }

    /**
     * This shit below is to get a valid 2x2 hatch hole. Welcome any PR for better solution.
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null){
            if (!worldIn.isRemote){
                worldIn.setBlockState(pos,Blocks.AIR.getDefaultState());
                ArrayList<Integer> list = this.getValidPos(worldIn, pos);
                //Seek for a complete 2x2 space.
                /*final int[] NW = {1,2,4,5};
                final int[] NE = {2,3,5,6};
                final int[] SW = {4,5,7,8};
                final int[] SE = {5,6,8,9};*/
                if (list.contains(1)&&list.contains(2)&&list.contains(4)&&list.contains(5)){
                    this.setHatch(worldIn,pos,"NW",stack);
                } else if (list.contains(2)&&list.contains(3)&&list.contains(5)&&list.contains(6)){
                    this.setHatch(worldIn,pos,"NE",stack);
                } else if (list.contains(4)&&list.contains(5)&&list.contains(7)&&list.contains(8)){
                    this.setHatch(worldIn,pos,"SW",stack);
                } else if (list.contains(5)&&list.contains(6)&&list.contains(8)&&list.contains(9)){
                    this.setHatch(worldIn,pos,"SE",stack);
                } else{
                    ItemStack returnStack = new ItemStack(stack.getItem());
                    ((PlayerEntity)placer).addItemStackToInventory(returnStack);
                }
            }
        }
    }

    /**
     * 1   2   3
     * 4   5   6
     * 7   8   9
     * Return all valid position by an Arraylist with position number.
     */
    private ArrayList<Integer> getValidPos(World worldIn, BlockPos pos){
        ArrayList<Integer> list = new ArrayList<>();
        for (int z=0;z<=6;z+=3){
            for (int x=1;x<=3;x++){
                BlockPos npos = pos;
                switch (x){
                    case 1:
                        npos = npos.offset(Direction.WEST);
                        break;
                    case 2:
                        break;
                    case 3:
                        npos = npos.offset(Direction.EAST);
                        break;
                    default:
                        break;
                }
                switch (z){
                    case 0:
                        npos = npos.offset(Direction.NORTH);
                        break;
                    case 3:
                        break;
                    case 6:
                        npos = npos.offset(Direction.SOUTH);
                        break;
                    default:
                        break;
                }
                if (worldIn.getBlockState(npos).getBlock() == Blocks.AIR){
                    list.add(x+z);
                }
            }
        }
        list.add(5);
        Collections.sort(list);
        return list;
    }

    /**
     * NW   1   2   -
     *      4   5   -
     *      -   -   -
     *
     * NE   -   2   3
     *      -   5   6
     *      -   -   -
     *
     * SW   -   -   -
     *      4   5   -
     *      7   8   -
     *
     *      -   -   -
     *  SE  -   5   6
     *      -   8   9
     *
     *      +---+---+
     *      | N + E |
     *      | W + S |
     *      +---+---+
     */
    private void setHatch(World worldIn, BlockPos pos,String s,ItemStack stack){
        //:(
        switch (s){
            case "NW":
                worldIn.setBlockState(pos.offset(Direction.NORTH).offset(Direction.WEST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.SOUTH));
                worldIn.setBlockState(pos.offset(Direction.NORTH),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.WEST));
                worldIn.setBlockState(pos.offset(Direction.WEST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.EAST));
                worldIn.setBlockState(pos,this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.NORTH));
                break;
            case "NE":
                worldIn.setBlockState(pos.offset(Direction.NORTH),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.SOUTH));
                worldIn.setBlockState(pos.offset(Direction.NORTH).offset(Direction.EAST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.WEST));
                worldIn.setBlockState(pos,this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.EAST));
                worldIn.setBlockState(pos.offset(Direction.EAST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.NORTH));
                break;
            case "SW":
                worldIn.setBlockState(pos.offset(Direction.WEST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.SOUTH));
                worldIn.setBlockState(pos,this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.WEST));
                worldIn.setBlockState(pos.offset(Direction.SOUTH).offset(Direction.WEST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.EAST));
                worldIn.setBlockState(pos.offset(Direction.SOUTH),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.NORTH));
                break;
            case "SE":
                worldIn.setBlockState(pos,this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.SOUTH));
                worldIn.setBlockState(pos.offset(Direction.EAST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.WEST));
                worldIn.setBlockState(pos.offset(Direction.SOUTH),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.EAST));
                worldIn.setBlockState(pos.offset(Direction.SOUTH).offset(Direction.EAST),this.getWood(stack.getItem().getRegistryName().toString()).getDefaultState().with(BlockStateProperties.FACING,Direction.NORTH));
                break;
            default:
                break;
        }
    }

    private Block getWood(String regName){
        switch (regName){
            case "rainbow6:oak_hatch":
                return ModBlocks.oakHatch;
            case "rainbow6:spruce_hatch":
                return ModBlocks.spruceHatch;
            case "rainbow6:birch_hatch":
                return ModBlocks.birchHatch;
            case "rainbow6:jungle_hatch":
                return ModBlocks.jungleHatch;
            case "rainbow6:acacia_hatch":
                return ModBlocks.acaciaHatch;
            case "rainbow6:dark_oak_hatch":
                return ModBlocks.darkoakHatch;
            case "rainbow6:crimson_hatch":
                return ModBlocks.crimsonHatch;
            case "rainbow6:warped_hatch":
                return ModBlocks.warpedHatch;
            default:
                return ModBlocks.oakHatch;
        }
    }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity){
        Vector3d vec =entity.getPositionVec();
        return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()),(float) (vec.y - clickedBlock.getY()),(float) (vec.z - clickedBlock.getZ()));
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
