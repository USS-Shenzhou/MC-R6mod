package com.ussshenzhou.rainbow6.tileentities;

import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntityType;
/**
 * @author USS_Shenzhou
 */
public class ModTileEntityTypes {
    public static TileEntityType BlackMirrorTileEntityType = TileEntityType.Builder.create(BannerTileEntity::new,ModBlocks.blackMirror).build(null).setRegistryName(ModBlocks.blackMirror.getRegistryName());
    public static TileEntityType reinforcementTileEntityType = TileEntityType.Builder.create(ReinforcementTileEntity::new, ModBlocks.reinforcement).build(null).setRegistryName(ModBlocks.reinforcement.getRegistryName());
}