package com.ussshenzhou.rainbow6.blocks;

import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.tileentities.BlackMirrorTileEntity;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlackMirror extends Block {
    public static BooleanProperty LEFT = BooleanProperty.create("left");
    public static BooleanProperty BROKEN = BooleanProperty.create("broken");

    protected static final VoxelShape NORTH = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 6.0D);
    protected static final VoxelShape WEST = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST = Block.makeCuboidShape(10.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);

    protected static final VoxelShape L_N_BROKEN1 = Block.makeCuboidShape(0.0D,0.0D,6.0D,16.0D,1.0D,10.0D);
    protected static final VoxelShape L_N_BROKEN2 = Block.makeCuboidShape(0.0D,15.0D,6.0D,16.0D,16.0D,10.0D);
    protected static final VoxelShape L_N_BROKEN3 = Block.makeCuboidShape(15.0D,1.0D,6.0D,16.0D,15.0D,10.0D);
    protected static final VoxelShape L_S_BROKEN1 = Block.makeCuboidShape(0.0D,0.0D,6.0D,16.0D,1.0D,10.0D);
    protected static final VoxelShape L_S_BROKEN2 = Block.makeCuboidShape(0.0D,15.0D,6.0D,16.0D,16.0D,10.0D);
    protected static final VoxelShape L_S_BROKEN3 = Block.makeCuboidShape(0.0D,1.0D,6.0D,1.0D,15.0D,10.0D);
    protected static final VoxelShape L_W_BROKEN1 = Block.makeCuboidShape(6.0D,0.0D,0.0D,10.0D,1.0D,16.0D);
    protected static final VoxelShape L_W_BROKEN2 = Block.makeCuboidShape(6.0D,15.0D,0.0D,10.0D,16.0D,16.0D);
    protected static final VoxelShape L_W_BROKEN3 = Block.makeCuboidShape(6.0D,1.0D,0.0D,10.0D,15.0D,1.0D);
    protected static final VoxelShape L_E_BROKEN1 = Block.makeCuboidShape(6.0D,0.0D,0.0D,10.0D,1.0D,16.0D);
    protected static final VoxelShape L_E_BROKEN2 = Block.makeCuboidShape(6.0D,15.0D,0.0D,10.0D,16.0D,16.0D);
    protected static final VoxelShape L_E_BROKEN3 = Block.makeCuboidShape(6.0D,0.0D,16.0D,10.0D,1.0D,15.0D);
    protected static final VoxelShape LEFT_NORTH_BROKEN = VoxelShapes.or(L_N_BROKEN1, L_N_BROKEN2, L_N_BROKEN3);
    protected static final VoxelShape LEFT_SOUTH_BROKEN = VoxelShapes.or(L_S_BROKEN1, L_S_BROKEN2, L_S_BROKEN3);
    protected static final VoxelShape LEFT_WEST_BROKEN = VoxelShapes.or(L_W_BROKEN1, L_W_BROKEN2, L_W_BROKEN3);
    protected static final VoxelShape LEFT_EAST_BROKEN = VoxelShapes.or(L_E_BROKEN1, L_E_BROKEN2, L_E_BROKEN3);

    protected static final VoxelShape R_S_BROKEN1 = Block.makeCuboidShape(0.0D,0.0D,6.0D,16.0D,1.0D,10.0D);
    protected static final VoxelShape R_S_BROKEN2 = Block.makeCuboidShape(0.0D,15.0D,6.0D,16.0D,16.0D,10.0D);
    protected static final VoxelShape R_S_BROKEN3 = Block.makeCuboidShape(15.0D,1.0D,6.0D,16.0D,15.0D,10.0D);
    protected static final VoxelShape R_N_BROKEN1 = Block.makeCuboidShape(0.0D,0.0D,6.0D,16.0D,1.0D,10.0D);
    protected static final VoxelShape R_N_BROKEN2 = Block.makeCuboidShape(0.0D,15.0D,6.0D,16.0D,16.0D,10.0D);
    protected static final VoxelShape R_N_BROKEN3 = Block.makeCuboidShape(0.0D,1.0D,6.0D,1.0D,15.0D,10.0D);
    protected static final VoxelShape R_E_BROKEN1 = Block.makeCuboidShape(6.0D,0.0D,0.0D,10.0D,1.0D,16.0D);
    protected static final VoxelShape R_E_BROKEN2 = Block.makeCuboidShape(6.0D,15.0D,0.0D,10.0D,16.0D,16.0D);
    protected static final VoxelShape R_E_BROKEN3 = Block.makeCuboidShape(6.0D,1.0D,0.0D,10.0D,15.0D,1.0D);
    protected static final VoxelShape R_W_BROKEN1 = Block.makeCuboidShape(6.0D,0.0D,0.0D,10.0D,1.0D,16.0D);
    protected static final VoxelShape R_W_BROKEN2 = Block.makeCuboidShape(6.0D,15.0D,0.0D,10.0D,16.0D,16.0D);
    protected static final VoxelShape R_W_BROKEN3 = Block.makeCuboidShape(6.0D,0.0D,16.0D,10.0D,1.0D,15.0D);
    protected static final VoxelShape RIGHT_NORTH_BROKEN = VoxelShapes.or(R_N_BROKEN1, R_N_BROKEN2, R_N_BROKEN3);
    protected static final VoxelShape RIGHT_SOUTH_BROKEN = VoxelShapes.or(R_S_BROKEN1, R_S_BROKEN2, R_S_BROKEN3);
    protected static final VoxelShape RIGHT_WEST_BROKEN= VoxelShapes.or(R_W_BROKEN1, R_W_BROKEN2, R_W_BROKEN3);
    protected static final VoxelShape RIGHT_EAST_BROKEN= VoxelShapes.or(R_E_BROKEN1, R_E_BROKEN2, R_E_BROKEN3);

    public BlackMirror() {
        super(Properties.create(Material.IRON)
        .harvestLevel(3)
                .hardnessAndResistance(40.0f,7.0f)
                .notSolid()
        );
        this.setRegistryName("blackmirror");
        this.setDefaultState(this.getStateContainer().getBaseState().with(LEFT,true).with(BROKEN,false).with(BlockStateProperties.FACING,Direction.NORTH));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer!= null){
            Direction direction = getFacingFromEntity(pos,placer);
            //wall to deploy MUST NOT harder than Terracotta
            float maxDeployableResistance = 4.2f;
            //this long-long shit just to get the ExplosionResistance of the front block, please tell me if there is a easier method.
            if (direction == Direction.UP||direction == Direction.DOWN||worldIn.getBlockState(pos.offset(direction.getOpposite())).getExplosionResistance(worldIn,pos.offset(direction.getOpposite()),null,new Explosion(worldIn,null,pos.offset(direction.getOpposite()).getX(),pos.offset(direction.getOpposite()).getY(),pos.offset(direction.getOpposite()).getZ(),0,false, Explosion.Mode.DESTROY))>=maxDeployableResistance){
                if (!worldIn.isRemote){
                    worldIn.removeBlock(pos,false);
                    worldIn.removeTileEntity(pos);
                }
                cancelPlace((PlayerEntity)placer);
            }
            else{
                worldIn.setBlockState(pos,state.with(BlockStateProperties.FACING,direction).with(LEFT,true).with(BROKEN,false),2);
                if (worldIn.isRemote){
                    worldIn.playSound(pos.getX(),pos.getY(),pos.getZ(),ModSounds.BLACKMIRROR_SET, SoundCategory.PLAYERS,1.0f,1.0f,true);
                }
            }
        }
    }

    public void cancelPlace(PlayerEntity player){
        ItemStack returnStack = new ItemStack(ModItems.blackMirrorItem);
        player.addItemStackToInventory(returnStack);
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(BlockStateProperties.FACING);
        if (state.get(BROKEN)){
            if (state.get(LEFT)){
                switch(direction){
                    case NORTH:
                        return LEFT_NORTH_BROKEN;
                    case SOUTH:
                        return LEFT_SOUTH_BROKEN;
                    case EAST:
                        return LEFT_EAST_BROKEN;
                    case WEST:
                        return LEFT_WEST_BROKEN;
                    default:
                        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
                }
            }
            else{
                switch(direction){
                    case NORTH:
                        return RIGHT_NORTH_BROKEN;
                    case SOUTH:
                        return RIGHT_SOUTH_BROKEN;
                    case EAST:
                        return RIGHT_EAST_BROKEN;
                    case WEST:
                        return RIGHT_WEST_BROKEN;
                    default:
                        return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
                }
            }
        }
        else{
            switch(direction){
                case NORTH:
                    return NORTH;
                case SOUTH:
                    return SOUTH;
                case EAST:
                    return EAST;
                case WEST:
                    return WEST;
                default:
                    return Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        //if (hit.getPos().getY()){}
        if (state.get(LEFT) && handIn == Hand.MAIN_HAND && hit.getFace()==state.get(BlockStateProperties.FACING)){
            BlackMirrorTileEntity blackMirrorTileEntity = (BlackMirrorTileEntity)worldIn.getTileEntity(pos);
            blackMirrorTileEntity.increaseCounter();
            if (blackMirrorTileEntity.getCounter()==3){
                breakMirror(state,worldIn,pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    public static void breakMirror(BlockState state, World worldIn, BlockPos pos){
        if (worldIn.isRemote){
            worldIn.getWorld().playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_GLASS_BREAK,SoundCategory.PLAYERS,1.0f,1.0f,false);
        }
        else{
            Direction dir = state.get(BlockStateProperties.FACING);
            worldIn.destroyBlock(pos,false);
            worldIn.setBlockState(pos,ModBlocks.blackMirror.getDefaultState().with(BlockStateProperties.FACING,dir).with(LEFT,true).with(BROKEN,true));
            worldIn.removeTileEntity(pos);
            BlockPos rPos = pos;
            switch(dir){
                case NORTH:
                    rPos=pos.add(-1,0,0);
                    break;
                case SOUTH:
                    rPos=pos.add(1,0,0);
                    break;
                case EAST:
                    rPos=pos.add(0,0,-1);
                    break;
                case WEST:
                    rPos=pos.add(0,0,1);
                    break;
                default:
            }
            worldIn.destroyBlock(rPos,false);
            worldIn.setBlockState(rPos,ModBlocks.blackMirror.getDefaultState().with(BlockStateProperties.FACING,dir).with(LEFT,false).with(BROKEN,true));
            worldIn.removeTileEntity(rPos);
        }
    }

    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity){
        Vec3d vec = entity.getPositionVec();
        return Direction.getFacingFromVector((float) (vec.x - clickedBlock.getX()),(float) (vec.y - clickedBlock.getY()),(float) (vec.z - clickedBlock.getZ()));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        builder.add(LEFT);
        builder.add(BROKEN);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlackMirrorTileEntity();
    }
}
