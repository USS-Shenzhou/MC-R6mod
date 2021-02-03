package com.ussshenzhou.rainbow6.tileentities;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.blocks.Reinforcement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
/**
 * @author USS_Shenzhou
 */
public class ReinforcementTileEntity extends TileEntity implements ITickableTileEntity {
    public int startTime = 0;
    protected static final VoxelShape FULL = Block.makeCuboidShape(0.0D,0.0D,0.0D,16.0D,16.0D,16.0D);
    public ReinforcementTileEntity() {
        super(ModTileEntityTypes.reinforcementTileEntityType);
    }

    @Override
    public void tick() {
        BlockPos upPos = pos.add(0,1,0);
        BlockPos lowerPos = pos.add(0,-1,0);
        Direction dir = world.getBlockState(pos).get(BlockStateProperties.FACING);
        if (!world.isRemote){
            switch (startTime){
                case 64:
                    if (!world.getBlockState(pos).get(Reinforcement.upper)) {
                        world.setBlockState(upPos, ModBlocks.reinforcement.getDefaultState().with(BlockStateProperties.FACING, dir));
                    }
                    break;
                case 96:
                    if (world.getBlockState(lowerPos).getShape(world,lowerPos)!=FULL && !world.getBlockState(pos).get(Reinforcement.upper)){
                        world.destroyBlock(lowerPos,false);
                        world.setBlockState(lowerPos,ModBlocks.reinforcement.getDefaultState().with(BlockStateProperties.FACING,dir));
                    }
                    break;
                default:
                    break;
            }
            startTime++;
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        startTime = nbt.getInt("startTime");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("startTime",startTime);
        return super.write(compound);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos().add(-2,1,-2),getPos().add(2,1,2));
    }
}
