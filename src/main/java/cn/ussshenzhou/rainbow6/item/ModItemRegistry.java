package cn.ussshenzhou.rainbow6.item;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, R6Constants.MOD_ID);
    //public static final RegistryObject<SkinTestItem> SKIN_TEST = ITEMS.register("skin_test", SkinTestItem::new);

    public static CreativeModeTab GENERAL_TAB;

    @SubscribeEvent
    public static void putItemsIntoTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == GENERAL_TAB) {
            //event.accept(SKIN_TEST);
        }
    }

    @SubscribeEvent
    public static void tabRegistry(CreativeModeTabEvent.Register event) {
        GENERAL_TAB = event.registerCreativeModeTab(new ResourceLocation(R6Constants.MOD_ID, "general_tab"), builder -> {

        });
    }
}
