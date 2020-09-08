package com.ussshenzhou.rainbow6.blocks;

import com.ussshenzhou.rainbow6.capability.IR6PlayerCapability;
import com.ussshenzhou.rainbow6.capability.ModCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;

public class CapabilityTestBlock extends Block {
    public CapabilityTestBlock() {
        super(Properties.create(Material.IRON)
        );
        this.setRegistryName("capabilitytestblock");
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote){
            LazyOptional<IR6PlayerCapability> r6PlayerCap = player.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
            r6PlayerCap.ifPresent((cap)->{
                        cap.setR6Team("attacker");
                        cap.setOperator("mira");
                    }
            );
        }
        return ActionResultType.SUCCESS;
    }
}
