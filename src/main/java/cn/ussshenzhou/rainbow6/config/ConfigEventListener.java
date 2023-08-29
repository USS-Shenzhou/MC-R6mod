package cn.ussshenzhou.rainbow6.config;

import cn.ussshenzhou.rainbow6.event.OptionInstanceChangeEvent;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.t88.config.ConfigHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ConfigEventListener {

    @SubscribeEvent
    public static void onVanillaOptionChange(OptionInstanceChangeEvent<?> event) {
        var o = Minecraft.getInstance().options;
        if (event.optionInstance == o.toggleSprint()) {
            var v = (Boolean) event.value;
            ConfigHelper.Universal.getConfigRead(Control.class).sprint = v ? KeyTrig.TOGGLE : KeyTrig.HOLD;
        } else if (event.optionInstance == o.toggleCrouch()) {
            var v = (Boolean) event.value;
            ConfigHelper.Universal.getConfigRead(Control.class).crouch = v ? KeyTrig.TOGGLE : KeyTrig.HOLD;
        }
    }
}
