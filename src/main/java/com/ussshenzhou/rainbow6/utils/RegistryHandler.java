package com.ussshenzhou.rainbow6.utils;

import com.ussshenzhou.rainbow6.armors.ModArmors;
import com.ussshenzhou.rainbow6.blocks.ModBlocks;
import com.ussshenzhou.rainbow6.bullets.ModBulletEntityTypes;
import com.ussshenzhou.rainbow6.effects.ModEffects;
import com.ussshenzhou.rainbow6.entities.ModEntityTypes;
import com.ussshenzhou.rainbow6.items.ModItems;
import com.ussshenzhou.rainbow6.tileentities.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
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
                ModBlocks.crimsonPlanksFloor,
                ModBlocks.warpedPlanksFloor,

                ModBlocks.oakHatch,
                ModBlocks.spruceHatch,
                ModBlocks.birchHatch,
                ModBlocks.jungleHatch,
                ModBlocks.acaciaHatch,
                ModBlocks.darkoakHatch,
                ModBlocks.crimsonHatch,
                ModBlocks.warpedHatch,
                ModBlocks.brokenHatch,

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
                ModItems.fragGrenade,
                ModItems.remoteGasGrenadeItem,
                ModItems.exploder,
                ModItems.proximityAlarmItem,
                ModItems.smokeGrenade,
                ModItems.droneItem,
                ModItems.r6CellPhone,

                ModItems.oakPlanksFloor,
                ModItems.sprucePlanksFloor,
                ModItems.birchPlanksFloor,
                ModItems.junglePlanksFloor,
                ModItems.acaciaPlanksFloor,
                ModItems.darkOakPlanksFloor,
                ModItems.crimsonPlanksFloor,
                ModItems.warpedPlanksFloor,

                ModItems.oakHatch,
                ModItems.spruceHatch,
                ModItems.birchHatch,
                ModItems.jungleHatch,
                ModItems.acaciaHatch,
                ModItems.darkoakHatch,
                ModItems.crimsonHatch,
                ModItems.warpedHatch
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
                ModSounds.EXPLODER_CLICK,
                ModSounds.PROXIMITY_ALARM,
                ModSounds.R6BUTTON_DOWN,
                ModSounds.MATCHMADE,
                ModSounds.DRONE_HIT,
                ModSounds.DRONE_MOVE
        );
    }
    @SubscribeEvent
    public static void onEntityTypeReg(RegistryEvent.Register<EntityType<?>> event){
        event.getRegistry().registerAll(
                ModEntityTypes.impactGrenadeEntityType,
                ModEntityTypes.nitroCellEntityType,
                ModEntityTypes.fragGrenadeEntityType,
                ModEntityTypes.remoteGasGrenadeEntityType,
                ModEntityTypes.proximityAlarmEntityType,
                ModEntityTypes.smokeGrenadeEntityType,
                ModEntityTypes.droneEntityType,
                ModBulletEntityTypes.testbulletEntityType
        );
    }
    @SubscribeEvent
    public static void onTileEntityReg(RegistryEvent.Register<TileEntityType<?>> event){
        event.getRegistry().registerAll(
                ModTileEntityTypes.reinforcementTileEntityType,
                ModTileEntityTypes.BlackMirrorTileEntityType
        );
    }
    @SubscribeEvent
    public static void onEffectReg(RegistryEvent.Register<Effect> event){
         event.getRegistry().registerAll(
                 ModEffects.EXPOSED
         );
    }
    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.ironBlock, RenderType.getTranslucent());
    }*/

}
