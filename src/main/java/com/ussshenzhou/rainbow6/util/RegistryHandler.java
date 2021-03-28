package com.ussshenzhou.rainbow6.util;

import com.mojang.brigadier.arguments.ArgumentType;
import com.ussshenzhou.rainbow6.armors.ModArmors;
import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.tileentities.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onBlockReg(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
                ModBlocks.barricade,
                ModBlocks.ironBlock,
                ModBlocks.reinforcement,
                ModBlocks.blackMirror,
                ModBlocks.oakPlanksFloor,
                ModBlocks.sprucePlanksFloor,
                ModBlocks.birchPlanksFloor,
                ModBlocks.junglePlanksFloor,
                ModBlocks.acaciaPlanksFloor,
                ModBlocks.darkOakPlanksFloor,
                ModBlocks.ironBarFloor
        );
    }
    @SubscribeEvent
    public static void onItemReg(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                ModItems.logo,
                ModItems.barricadeItem,
                ModItems.ironBlock,
                ModItems.reinforcementItem,
                ModItems.impactGrenade,
                ModItems.nitroCell,
                ModItems.nitroCellExploder,
                ModItems.blackMirrorItem,
                //ModItems.capabilityTestBlockItem,
                ModItems.fragGrenade,
                ModItems.remoteGasGrenadeItem,
                ModItems.exploder,
                ModItems.oakPlanksFloor,
                ModItems.sprucePlanksFloor,
                ModItems.birchPlanksFloor,
                ModItems.junglePlanksFloor,
                ModItems.acaciaPlanksFloor,
                ModItems.darkOakPlanksFloor
        );
    }
    /**
     * same as ItemReg
    */
     @SubscribeEvent
    public static void onArmorReg(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                ModArmors.ashHead,
                ModArmors.ashChest
        );
    }

    @SubscribeEvent
    public static void onSoundsReg(RegistryEvent.Register<SoundEvent> event){
        event.getRegistry().registerAll(
                ModSounds.MUTE,
                ModSounds.BARRICADE_BREAK,
                ModSounds.BARRICADE_PLACE,
                ModSounds.IMPACT_GRENADE_THROW,
                ModSounds.NITRO_CELL_THROW,
                ModSounds.NITRO_CELL_HIT,
                ModSounds.REINFORCEMENT_PLACE,
                ModSounds.BLACKMIRROR_SET,
                ModSounds.FRAGGRENADE_READY,
                ModSounds.FRAGGRENADE_TOUCH,
                ModSounds.REMOTEGASGRENADE_THROW,
                ModSounds.REMOTEGASGRENADE_EXPLODE,
                ModSounds.EXPLODER_CLICK
        );
    }
    @SubscribeEvent
    public static void onEntityTypeReg(RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(
                ModEntityTypes.impactGrenadeEntityType,
                ModEntityTypes.nitroCellEntityType,
                ModEntityTypes.fragGrenadeEntityType,
                ModEntityTypes.remoteGasGrenadeEntityType
        );
    }
    @SubscribeEvent
    public static void onTileEntityReg(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().registerAll(
                ModTileEntityTypes.reinforcementTileEntityType,
                ModTileEntityTypes.BlackMirrorTileEntityType
        );
    }
    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.ironBlock, RenderType.getTranslucent());
    }*/

}
