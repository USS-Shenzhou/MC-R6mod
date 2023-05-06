package cn.ussshenzhou.rainbow6.item;

import cn.ussshenzhou.rainbow6.item.armor.TestArmor;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, R6Constants.MOD_ID);
    public static CreativeModeTab GENERAL_TAB;
    //public static final RegistryObject<SkinTestItem> SKIN_TEST = ITEMS.register("skin_test", SkinTestItem::new);
    public static CreativeModeTab ARMOR_TAB;
    public static final RegistryObject<TestArmor> TEST_ARMOR_HELMET = ITEMS.register("test_armor_helmet", () -> new TestArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<TestArmor> TEST_ARMOR_CHESTPLATE = ITEMS.register("test_armor_chestplate", () -> new TestArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<TestArmor> TEST_ARMOR_LEGGINGS = ITEMS.register("test_armor_leggings", () -> new TestArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<TestArmor> TEST_ARMOR_BOOTS = ITEMS.register("test_armor_boots", () -> new TestArmor(ArmorItem.Type.BOOTS));

    @SubscribeEvent
    public static void putItemsIntoTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == GENERAL_TAB) {
            //event.accept(SKIN_TEST);
        } else if (event.getTab() == ARMOR_TAB) {
            List.of(
                    TEST_ARMOR_HELMET,
                    TEST_ARMOR_CHESTPLATE,
                    TEST_ARMOR_LEGGINGS,
                    TEST_ARMOR_BOOTS
            ).forEach(event::accept);
        }
    }

    @SubscribeEvent
    public static void tabRegistry(CreativeModeTabEvent.Register event) {
        GENERAL_TAB = event.registerCreativeModeTab(new ResourceLocation(R6Constants.MOD_ID, "general_tab"), builder -> {

        });
        ARMOR_TAB = event.registerCreativeModeTab(new ResourceLocation(R6Constants.MOD_ID, "armor_tab"), builder -> {

        });
    }
}
