package cn.ussshenzhou.rainbow6.item;

import cn.ussshenzhou.rainbow6.item.armor.BaseR6ArmorItem;
import cn.ussshenzhou.rainbow6.item.armor.ModItemInventoryModelRegistry;
import cn.ussshenzhou.rainbow6.item.armor.TestArmor;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemRegistry {
    public static LinkedList<RegistryObject<BaseR6ArmorItem>> ARMORS_TO_TAB = new LinkedList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, R6Constants.MOD_ID);

    //public static final RegistryObject<SkinTestItem> SKIN_TEST = ITEMS.register("skin_test", SkinTestItem::new);
    public static final RegistryObject<TestArmor> TEST_ARMOR_HELMET = registryAndHasSpecialHandModelArmor("test_helmet", () -> new TestArmor(ArmorItem.Type.HELMET));
    public static final RegistryObject<TestArmor> TEST_ARMOR_CHESTPLATE = registryAndHasSpecialHandModelArmor("test_chestplate", () -> new TestArmor(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<TestArmor> TEST_ARMOR_LEGGINGS = registryAndHasSpecialHandModelArmor("test_leggings", () -> new TestArmor(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<TestArmor> TEST_ARMOR_BOOTS = registryAndHasSpecialHandModelArmor("test_boots", () -> new TestArmor(ArmorItem.Type.BOOTS));


    public static final RegistryObject<Item> OLD_LOGO = ITEMS.register("old_logo", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OLD_LOGO_ARMOR = ITEMS.register("old_logo_armor", () -> new Item(new Item.Properties()));

    //----------creative tabs----------

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, R6Constants.MOD_ID);
    public static RegistryObject<CreativeModeTab> GENERAL_TAB = TABS.register("general", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(OLD_LOGO.get()))
            .title(Component.translatable("tab.r6ms.general"))
            .build()
    );
    public static RegistryObject<CreativeModeTab> ARMOR_TAB = TABS.register("armor", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(OLD_LOGO_ARMOR.get()))
            .title(Component.translatable("tab.r6ms.armor"))
            .withTabsBefore(GENERAL_TAB.getId())
            .build()
    );



    @SubscribeEvent
    public static void putItemsIntoTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == GENERAL_TAB.get()) {
            //event.accept(SKIN_TEST);
            event.accept(TEST_ARMOR_HELMET.get());
        } else if (event.getTab() == ARMOR_TAB.get()) {
            ARMORS_TO_TAB.forEach(event::accept);
        }
    }

    private static <T extends Item> RegistryObject<T> registryAndHasSpecialHandModel(String name, Supplier<T> supplier) {
        var o = ITEMS.register(name, supplier);
        ModItemInventoryModelRegistry.HAVE_SPECIAL_HAND_MODEL.add(o);
        return o;
    }

    private static <T extends BaseR6ArmorItem> RegistryObject<T> registryAndHasSpecialHandModelArmor(String name, Supplier<T> supplier) {
        var o = ITEMS.register(name, supplier);
        ModItemInventoryModelRegistry.HAVE_SPECIAL_HAND_MODEL.add(o);
        //noinspection unchecked
        ARMORS_TO_TAB.add((RegistryObject<BaseR6ArmorItem>) o);
        return o;
    }
}
