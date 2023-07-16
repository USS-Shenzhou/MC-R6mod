package cn.ussshenzhou.rainbow6.gun.item;

import cn.ussshenzhou.rainbow6.item.ModItemRegistry;
import cn.ussshenzhou.rainbow6.item.armor.BaseR6ArmorItem;
import cn.ussshenzhou.rainbow6.item.armor.ModItemInventoryModelRegistry;
import cn.ussshenzhou.rainbow6.item.armor.TestArmor;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GunRegistry {
    public static final DeferredRegister<Item> ITEMS = ModItemRegistry.ITEMS;

    public static final RegistryObject<TestGun> TEST_GUN = ITEMS.register("test_gun", TestGun::new);

    //----------creative tabs----------

    public static final DeferredRegister<CreativeModeTab> TABS = ModItemRegistry.TABS;
    public static RegistryObject<CreativeModeTab> WEAPON_TAB = TABS.register("weapon", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItemRegistry.OLD_LOGO.get()))
            .title(Component.translatable("tab.r6ms.weapon"))
            .build()
    );


    @SubscribeEvent
    public static void putItemsIntoTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == WEAPON_TAB.get()) {
            event.accept(TEST_GUN.get());
        }
    }
}
