package com.ussshenzhou.rainbow6.bullets.penetrate;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;

/**
 * @author USS_Shenzhou
 *
 * ATTENTION: "harder" and "softer" here refers to Properties.resistance(for explosion), NOT Properties.hardness(for digging).
 * All blocks harder than 4.2 is impenetrable.
 * Softer blocks are penetrable by default.
 */
public class IsPenetrableBlocks {

    private static HashMap<Block,Integer> specialBlocks = new HashMap<Block,Integer>();

    public static int isPenetrable(Block block){
        return specialBlocks.getOrDefault(block, 1);
    }

    /**
     * -3:for glazed terracotta
     * -2:for bookshelf
     * -1:destroy after multiple penetration
     * 0:penetrate and destroy
     * 1:penetrate(default)
     * 2:impenetrable
     *
     * Slabs and Stairs is penetrable but un destroyable--only can be destroyed by explosion.
     */
    public static void createMap(){

        specialBlocks.put(Blocks.LODESTONE,2);
        specialBlocks.put(Blocks.BLAST_FURNACE,2);
        specialBlocks.put(Blocks.DISPENSER,2);
        specialBlocks.put(Blocks.DROPPER,2);
        specialBlocks.put(Blocks.FURNACE,2);
        specialBlocks.put(Blocks.OBSERVER,2);
        specialBlocks.put(Blocks.SMOKER,2);
        specialBlocks.put(Blocks.STONECUTTER,2);
        specialBlocks.put(Blocks.BEACON,2);
        //ore
        specialBlocks.put(Blocks.COAL_ORE,2);
        specialBlocks.put(Blocks.DIAMOND_ORE,2);
        specialBlocks.put(Blocks.EMERALD_ORE,2);
        specialBlocks.put(Blocks.GOLD_ORE,2);
        specialBlocks.put(Blocks.IRON_ORE,2);
        specialBlocks.put(Blocks.LAPIS_BLOCK,2);
        specialBlocks.put(Blocks.LAPIS_ORE,2);
        specialBlocks.put(Blocks.NETHER_QUARTZ_ORE,2);
        specialBlocks.put(Blocks.REDSTONE_ORE,2);
        //plank
        specialBlocks.put(Blocks.ACACIA_PLANKS,-1);
        specialBlocks.put(Blocks.BIRCH_PLANKS,-1);
        specialBlocks.put(Blocks.CRIMSON_PLANKS,-1);
        specialBlocks.put(Blocks.DARK_OAK_PLANKS,-1);
        specialBlocks.put(Blocks.JUNGLE_PLANKS,-1);
        specialBlocks.put(Blocks.OAK_PLANKS,-1);
        specialBlocks.put(Blocks.SPRUCE_PLANKS,-1);
        specialBlocks.put(Blocks.WARPED_PLANKS,-1);
        //fence
        specialBlocks.put(Blocks.ACACIA_FENCE,-1);
        specialBlocks.put(Blocks.BIRCH_FENCE,-1);
        specialBlocks.put(Blocks.CRIMSON_FENCE,-1);
        specialBlocks.put(Blocks.DARK_OAK_FENCE,-1);
        specialBlocks.put(Blocks.JUNGLE_FENCE,-1);
        specialBlocks.put(Blocks.OAK_FENCE,-1);
        specialBlocks.put(Blocks.SPRUCE_FENCE,-1);
        specialBlocks.put(Blocks.WARPED_FENCE,-1);
        //fence gate
        specialBlocks.put(Blocks.ACACIA_FENCE_GATE,-1);
        specialBlocks.put(Blocks.BIRCH_FENCE_GATE,-1);
        specialBlocks.put(Blocks.CRIMSON_FENCE_GATE,-1);
        specialBlocks.put(Blocks.DARK_OAK_FENCE_GATE,-1);
        specialBlocks.put(Blocks.JUNGLE_FENCE_GATE,-1);
        specialBlocks.put(Blocks.OAK_FENCE_GATE,-1);
        specialBlocks.put(Blocks.SPRUCE_FENCE_GATE,-1);
        specialBlocks.put(Blocks.WARPED_FENCE_GATE,-1);
        //door
        specialBlocks.put(Blocks.ACACIA_DOOR,-1);
        specialBlocks.put(Blocks.BIRCH_DOOR,-1);
        specialBlocks.put(Blocks.CRIMSON_DOOR,-1);
        specialBlocks.put(Blocks.DARK_OAK_DOOR,-1);
        specialBlocks.put(Blocks.JUNGLE_DOOR,-1);
        specialBlocks.put(Blocks.OAK_DOOR,-1);
        specialBlocks.put(Blocks.SPRUCE_DOOR,-1);
        specialBlocks.put(Blocks.WARPED_DOOR,-1);
        //trapdoor
        specialBlocks.put(Blocks.ACACIA_TRAPDOOR,-1);
        specialBlocks.put(Blocks.BIRCH_TRAPDOOR,-1);
        specialBlocks.put(Blocks.CRIMSON_TRAPDOOR,-1);
        specialBlocks.put(Blocks.DARK_OAK_TRAPDOOR,-1);
        specialBlocks.put(Blocks.JUNGLE_TRAPDOOR,-1);
        specialBlocks.put(Blocks.OAK_TRAPDOOR,-1);
        specialBlocks.put(Blocks.SPRUCE_TRAPDOOR,-1);
        specialBlocks.put(Blocks.WARPED_TRAPDOOR,-1);
        specialBlocks.put(Blocks.BLUE_ICE,-1);
        specialBlocks.put(Blocks.BARREL,2);
        specialBlocks.put(Blocks.CARTOGRAPHY_TABLE,2);
        specialBlocks.put(Blocks.CHEST,2);
        specialBlocks.put(Blocks.CRAFTING_TABLE,2);
        specialBlocks.put(Blocks.FLETCHING_TABLE,2);
        specialBlocks.put(Blocks.LOOM,2);
        specialBlocks.put(Blocks.SMITHING_TABLE,2);
        specialBlocks.put(Blocks.TRAPPED_CHEST,2);
        specialBlocks.put(Blocks.BONE_BLOCK,-1);
        specialBlocks.put(Blocks.CAULDRON,2);
        specialBlocks.put(Blocks.BOOKSHELF,-2);
        //!! glazed terracotta !!
        specialBlocks.put(Blocks.JACK_O_LANTERN,-1);
        specialBlocks.put(Blocks.MELON,-1);
        //!! HEAD !!
        specialBlocks.put(Blocks.NOTE_BLOCK,2);
        specialBlocks.put(Blocks.INFESTED_CHISELED_STONE_BRICKS,-1);
        specialBlocks.put(Blocks.INFESTED_COBBLESTONE,-1);
        specialBlocks.put(Blocks.INFESTED_CRACKED_STONE_BRICKS,-1);
        specialBlocks.put(Blocks.INFESTED_MOSSY_STONE_BRICKS,-1);
        specialBlocks.put(Blocks.INFESTED_STONE,-1);
        specialBlocks.put(Blocks.INFESTED_STONE_BRICKS,-1);
        specialBlocks.put(Blocks.GRASS_PATH,2);
        specialBlocks.put(Blocks.BEEHIVE,-1);
        specialBlocks.put(Blocks.BEE_NEST,0);
        specialBlocks.put(Blocks.CLAY,2);
        specialBlocks.put(Blocks.COMPOSTER,2);
        specialBlocks.put(Blocks.FARMLAND,2);
        specialBlocks.put(Blocks.GRASS_BLOCK,2);
        specialBlocks.put(Blocks.GRAVEL,2);
        specialBlocks.put(Blocks.WET_SPONGE,2);
        specialBlocks.put(Blocks.BREWING_STAND,0);
        specialBlocks.put(Blocks.CAKE,0);
        specialBlocks.put(Blocks.COARSE_DIRT,2);
        specialBlocks.put(Blocks.WHITE_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.ORANGE_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.MAGENTA_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.LIGHT_BLUE_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.YELLOW_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.LIME_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.PINK_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.GRAY_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.LIGHT_GRAY_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.CYAN_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.PURPLE_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.BLUE_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.BROWN_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.GREEN_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.RED_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.BLACK_CONCRETE_POWDER,2);
        specialBlocks.put(Blocks.WHITE_CONCRETE,2);
        specialBlocks.put(Blocks.ORANGE_CONCRETE,2);
        specialBlocks.put(Blocks.MAGENTA_CONCRETE,2);
        specialBlocks.put(Blocks.LIGHT_BLUE_CONCRETE,2);
        specialBlocks.put(Blocks.YELLOW_CONCRETE,2);
        specialBlocks.put(Blocks.LIME_CONCRETE,2);
        specialBlocks.put(Blocks.PINK_CONCRETE,2);
        specialBlocks.put(Blocks.GRAY_CONCRETE,2);
        specialBlocks.put(Blocks.LIGHT_GRAY_CONCRETE,2);
        specialBlocks.put(Blocks.CYAN_CONCRETE,2);
        specialBlocks.put(Blocks.PURPLE_CONCRETE,2);
        specialBlocks.put(Blocks.BLUE_CONCRETE,2);
        specialBlocks.put(Blocks.BROWN_CONCRETE,2);
        specialBlocks.put(Blocks.GREEN_CONCRETE,2);
        specialBlocks.put(Blocks.RED_CONCRETE,2);
        specialBlocks.put(Blocks.BLACK_CONCRETE,2);
        specialBlocks.put(Blocks.DIRT,2);
        specialBlocks.put(Blocks.FROSTED_ICE,0);
        specialBlocks.put(Blocks.ICE,-1);
        specialBlocks.put(Blocks.MAGMA_BLOCK,2);
        specialBlocks.put(Blocks.MYCELIUM,2);
        specialBlocks.put(Blocks.PACKED_ICE,-1);
        specialBlocks.put(Blocks.PISTON,2);
        specialBlocks.put(Blocks.PISTON_HEAD,2);
        specialBlocks.put(Blocks.PODZOL,2);
        specialBlocks.put(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,2);
        specialBlocks.put(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,2);
        specialBlocks.put(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,2);
        specialBlocks.put(Blocks.STONE_PRESSURE_PLATE,2);
        specialBlocks.put(Blocks.OAK_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.SPRUCE_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.BIRCH_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.JUNGLE_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.ACACIA_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.DARK_OAK_PRESSURE_PLATE,0);
        specialBlocks.put(Blocks.SAND,2);
        specialBlocks.put(Blocks.SOUL_SAND,2);
        specialBlocks.put(Blocks.SOUL_SOIL,2);
        specialBlocks.put(Blocks.STICKY_PISTON,2);
        specialBlocks.put(Blocks.TURTLE_EGG,0);
        specialBlocks.put(Blocks.CACTUS,-1);
        specialBlocks.put(Blocks.CHORUS_FLOWER,0);
        specialBlocks.put(Blocks.CHORUS_PLANT,2);
        specialBlocks.put(Blocks.LADDER,2);
        specialBlocks.put(Blocks.NETHERRACK,2);
        //glass pane
        specialBlocks.put(Blocks.GLASS_PANE,0);
        specialBlocks.put(Blocks.WHITE_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.ORANGE_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.MAGENTA_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.YELLOW_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.LIME_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.PINK_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.GRAY_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.CYAN_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.PURPLE_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.BLUE_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.BROWN_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.GREEN_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.RED_STAINED_GLASS_PANE,0);
        specialBlocks.put(Blocks.BLACK_STAINED_GLASS_PANE,0);
        //glass block is bulletproof
        specialBlocks.put(Blocks.GLASS,2);
        specialBlocks.put(Blocks.WHITE_STAINED_GLASS,2);
        specialBlocks.put(Blocks.ORANGE_STAINED_GLASS,2);
        specialBlocks.put(Blocks.MAGENTA_STAINED_GLASS,2);
        specialBlocks.put(Blocks.LIGHT_BLUE_STAINED_GLASS,2);
        specialBlocks.put(Blocks.YELLOW_STAINED_GLASS,2);
        specialBlocks.put(Blocks.LIME_STAINED_GLASS,2);
        specialBlocks.put(Blocks.PINK_STAINED_GLASS,2);
        specialBlocks.put(Blocks.GRAY_STAINED_GLASS,2);
        specialBlocks.put(Blocks.LIGHT_GRAY_STAINED_GLASS,2);
        specialBlocks.put(Blocks.CYAN_STAINED_GLASS,2);
        specialBlocks.put(Blocks.PURPLE_STAINED_GLASS,2);
        specialBlocks.put(Blocks.BLUE_STAINED_GLASS,2);
        specialBlocks.put(Blocks.BROWN_STAINED_GLASS,2);
        specialBlocks.put(Blocks.GREEN_STAINED_GLASS,2);
        specialBlocks.put(Blocks.RED_STAINED_GLASS,2);
        specialBlocks.put(Blocks.BLACK_STAINED_GLASS,2);
        specialBlocks.put(Blocks.DAYLIGHT_DETECTOR,-1);
        specialBlocks.put(Blocks.END_ROD,2);
        specialBlocks.put(Blocks.FLOWER_POT,0);
        specialBlocks.put(Blocks.HONEY_BLOCK,2);
        specialBlocks.put(Blocks.LILY_PAD,-1);
        specialBlocks.put(Blocks.MOVING_PISTON,2);
        specialBlocks.put(Blocks.BROWN_MUSHROOM,0);
        specialBlocks.put(Blocks.RED_MUSHROOM,0);
        specialBlocks.put(Blocks.BROWN_MUSHROOM_BLOCK,-1);
        specialBlocks.put(Blocks.RED_MUSHROOM_BLOCK,-1);
        specialBlocks.put(Blocks.MUSHROOM_STEM,2);
        specialBlocks.put(Blocks.REDSTONE_WIRE,0);
        specialBlocks.put(Blocks.COMPARATOR,0);
        specialBlocks.put(Blocks.REPEATER,0);
        specialBlocks.put(Blocks.SEA_PICKLE,0);
        specialBlocks.put(Blocks.SLIME_BLOCK,2);
        specialBlocks.put(Blocks.TNT,0);
        specialBlocks.put(Blocks.TRIPWIRE_HOOK,0);
        //SANDSTONE
        specialBlocks.put(Blocks.SANDSTONE,2);
        specialBlocks.put(Blocks.CUT_SANDSTONE,2);
        specialBlocks.put(Blocks.SMOOTH_SANDSTONE,2);
        specialBlocks.put(Blocks.CHISELED_SANDSTONE,2);
        specialBlocks.put(Blocks.RED_SANDSTONE,2);
        specialBlocks.put(Blocks.CUT_RED_SANDSTONE,2);
        specialBlocks.put(Blocks.SMOOTH_RED_SANDSTONE,2);
        specialBlocks.put(Blocks.CHISELED_RED_SANDSTONE,2);
        //specialBlocks.put(Blocks.);
    }

}
