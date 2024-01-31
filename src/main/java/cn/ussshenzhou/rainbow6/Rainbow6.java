package cn.ussshenzhou.rainbow6;

import cn.ussshenzhou.rainbow6.dataattachment.ModDataAttachments;
import cn.ussshenzhou.rainbow6.gun.entity.ModGunEntityTypeRegistry;
import cn.ussshenzhou.rainbow6.item.ModItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod("r6ms")
public class Rainbow6 {

    public Rainbow6(IEventBus bus) {
        System.setProperty("t88.test_screen_override", "cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen");

        ModItemRegistry.ITEMS.register(bus);
        ModItemRegistry.TABS.register(bus);
        ModGunEntityTypeRegistry.GUN_ENTITY_TYPES.register(bus);
        ModDataAttachments.ATTACHMENT_TYPES.register(bus);
    }
}
