package com.ussshenzhou.rainbow6.tileentities;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.blocks.Reinforcement;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class ReinforcementTileEntity extends TileEntity implements ITickableTileEntity {
    public int startTime = 0;
    public ReinforcementTileEntity() {
        super(ModTileEntityTypes.reinforcementTileEntityType);
    }

    @Override
    public void tick() {
        BlockPos U_Pos = pos.add(0,1,0);
        BlockPos LL_Pos = pos.add(0,-1,0);
        Direction dir = world.getBlockState(pos).get(BlockStateProperties.FACING);
        if (!world.isRemote){
            switch (startTime){
                case 64:
                    if (!world.getBlockState(pos).get(Reinforcement.upper)) {
                        world.setBlockState(U_Pos, ModBlocks.reinforcement.getDefaultState().with(BlockStateProperties.FACING, dir));
                    }
                    break;
                case 96:
                    if (world.getBlockState(LL_Pos)==Blocks.AIR.getDefaultState() && !world.getBlockState(pos).get(Reinforcement.upper)){
                        world.setBlockState(LL_Pos,ModBlocks.reinforcement.getDefaultState().with(BlockStateProperties.FACING,dir));
                    }
                    break;
                default:
                    break;
            }
            startTime++;
        }
    }
    @Override
    public void read(CompoundNBT compound) {
        startTime = compound.getInt("startTime");
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("startTime",startTime);
        return super.write(compound);
    }
}
