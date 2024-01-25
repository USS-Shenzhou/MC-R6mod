package cn.ussshenzhou.rainbow6.gun.item;

import cn.ussshenzhou.rainbow6.gun.data.FixedProperties;
import cn.ussshenzhou.rainbow6.item.ModItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGunItemRegistry {
    public static final DeferredRegister<Item> GUNS = ModItemRegistry.ITEMS;

    public static final Supplier<TestGun> TEST_GUN = GUNS.register("test_gun", () -> new TestGun(FixedProperties.TEST));

    //----------creative tabs----------

    public static final DeferredRegister<CreativeModeTab> GUN_TABS = ModItemRegistry.TABS;
    public static Supplier<CreativeModeTab> WEAPON_TAB = GUN_TABS.register("weapon", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItemRegistry.OLD_LOGO.get()))
            .title(Component.translatable("tab.r6ms.weapon"))
            .build()
    );


    @SubscribeEvent
    public static void putItemsIntoTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == WEAPON_TAB.get()) {
            Stream.of(TEST_GUN).forEach(r -> event.accept(initGunTags(r)));
        }
    }

    private static ItemStack initGunTags(Supplier<? extends TestGun> registryObject) {
        var item = registryObject.get();
        var stack = new ItemStack(item);
        item.additionalTagOnCreativeTab(stack);
        return stack;
    }
}
