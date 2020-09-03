package com.ussshenzhou.rainbow6.tileentities;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import net.minecraft.tileentity.TileEntityType;

public class ModTileEntityTypes {
    /*public static TileEntityType BlackMirrorTileEntityLeftType = TileEntityType.Builder.create(BlackMirrorTileEntity_left::new, ModBlocks.blackMirror_left).build(null).setRegistryName(ModBlocks.blackMirror_left.getRegistryName());
    public static TileEntityType BlackMirrorTileEntityRightType = TileEntityType.Builder.create(BlackMirrorTileEntity_left::new, ModBlocks.blackMirror_right).build(null).setRegistryName(ModBlocks.blackMirror_right.getRegistryName());*/
    public static TileEntityType reinforcementTileEntityType = TileEntityType.Builder.create(ReinforcementTileEntity::new, ModBlocks.reinforcement).build(null).setRegistryName(ModBlocks.reinforcement.getRegistryName());
}