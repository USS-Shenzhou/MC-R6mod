package cn.ussshenzhou.rainbow6;

import cn.ussshenzhou.rainbow6.gun.entity.ModGunEntityTypeRegistry;
import cn.ussshenzhou.rainbow6.gun.item.ModGunItemRegistry;
import cn.ussshenzhou.rainbow6.item.ModItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author USS_Shenzhou
 */
@Mod("r6ms")
public class Rainbow6 {

    public Rainbow6() {
        MinecraftForge.EVENT_BUS.register(this);
        System.setProperty("t88.test_screen_override", "cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen");

        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItemRegistry.ITEMS.register(bus);
        ModItemRegistry.TABS.register(bus);
        ModGunEntityTypeRegistry.GUN_ENTITY_TYPES.register(bus);
    }
}
