package com.ussshenzhou.rainbow6.tileentities;

import com.ussshenzhou.rainbow6.blocks.BlackMirror;
import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/**
 * @author USS_Shenzhou
 */
public class BlackMirrorTileEntity extends TileEntity implements ITickableTileEntity {
    private Boolean isMoved = false;
    private int startTime = 0;
    private int counter = 0;
    public BlackMirrorTileEntity() {
        super(ModTileEntityTypes.BlackMirrorTileEntityType);
    }

    @Override
    public void tick() {
        if (!world.getBlockState(pos).get(BlackMirror.BROKEN)){
            //using a debug stick to redirect may cause this problem
            try{
                if (startTime==0 && world.getBlockState(pos.offset(world.getBlockState(pos).get(BlockStateProperties.FACING).rotateY(),1)).getBlock() instanceof BlackMirror && !isMoved){
                    startTime=21;
                }
            }
            catch (IllegalStateException e){
                String posWrong = pos.toString();
                Minecraft.getInstance().player.sendChatMessage("WARNING! A BlackMirror Block with wrong direction detected at"+posWrong+"! Go check it if it has been removed automatically!");
                if (!world.isRemote){
                    world.removeBlock(pos,false);
                    world.removeTileEntity(pos);
                }
            }
            if(!this.isMoved){
                switch (startTime){
                    case 20:
                        if (!world.isRemote && world.getBlockState(pos).get(BlackMirror.LEFT)){
                            placeRightPart(world,pos,world.getBlockState(pos));
                        }
                        break;
                    case 41:
                        BlockPos newPos = pos.offset(this.getBlockState().get(BlockStateProperties.FACING).getOpposite());
                        if (!world.isRemote) {
                            //push wall
                            world.setBlockState(newPos.offset(this.getBlockState().get(BlockStateProperties.FACING).getOpposite()),world.getBlockState(newPos));
                            //push mirror
                            world.setBlockState(newPos, ModBlocks.blackMirror.getDefaultState().with(BlockStateProperties.FACING, this.getBlockState().get(BlockStateProperties.FACING)).with(BlackMirror.LEFT,world.getBlockState(pos).get(BlackMirror.LEFT)).with(BlackMirror.BROKEN,false));
                            world.setTileEntity(newPos,new BlackMirrorTileEntity());
                            ((BlackMirrorTileEntity)world.getTileEntity(newPos)).setIsMoved(true);
                            world.removeBlock(pos,false);
                        }
                        else{
                            addSteamParticle(world,pos);
                        }
                        break;
                    default:
                        break;
                }
            }
            else{
                if (startTime== 21 &&!world.isRemote){
                    world.destroyBlock(pos.offset(this.getBlockState().get(BlockStateProperties.FACING).getOpposite()),false);
                }
            }
            startTime++;
        }
    }
    public void addSteamParticle(World world,BlockPos pos){
        Direction direction= world.getBlockState(pos).get(BlockStateProperties.FACING);
        BlockPos rPos = pos;
        BlockPos lPos = pos;
        switch (direction){
            case WEST:
                rPos = pos.add(0,0,1);
                lPos = pos.add(0,0,0);
                break;
            case EAST:
                rPos = pos.add(0,0,0);
                lPos = pos.add(0,0,1);
                break;
            case SOUTH:
                rPos = pos.add(1,0,1);
                lPos = pos.add(0,0,1);
                break;
            case NORTH:
                rPos = pos.add(0,0,0);
                lPos = pos.add(1,0,0);
                break;
            default:
                break;
        }
        for (int i=0;i<4;i++){
            if (world.getBlockState(pos).get(BlackMirror.LEFT)){
                world.addParticle(ParticleTypes.CLOUD,lPos.getX(),lPos.getY()+Math.random(),lPos.getZ(),0,0,0);
            }
            else{
                world.addParticle(ParticleTypes.CLOUD,rPos.getX(),rPos.getY()+Math.random(),rPos.getZ(),0,0,0);
            }
        }
    }

    public void placeRightPart(World worldIn, BlockPos pos, BlockState state){
        BlockPos rPos = pos;
        Direction direction = state.get(BlockStateProperties.FACING);
        switch(direction){
            case NORTH:
                rPos = pos.add(-1,0,0);
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
                break;
        }
        worldIn.setBlockState(rPos, ModBlocks.blackMirror.getDefaultState().with(BlockStateProperties.FACING,direction).with(BlackMirror.LEFT,false).with(BlackMirror.BROKEN,false));
        worldIn.setTileEntity(rPos,new BlackMirrorTileEntity());
        state.updateNeighbours(worldIn, pos, 3);
        worldIn.notifyNeighborsOfStateChange(pos, Blocks.AIR);
    }
    public Boolean setIsMoved(Boolean bool){
        this.isMoved =bool;
        markDirty();
        return this.isMoved;
    }
    public Boolean getIsMoved(){
        return this.isMoved;
    }

    public void increaseCounter(){
        counter++;
        markDirty();
    }
    public int getCounter(){
        return this.counter;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        isMoved = compound.getBoolean("IsMoved");
        startTime = compound.getInt("startTime");
        counter = compound.getInt("counter");
        super.read(state, compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("IsMoved", isMoved);
        compound.putInt("startTime",startTime);
        compound.putInt("counter",counter);
        return super.write(compound);
    }
}
