package cn.ussshenzhou.rainbow6.datagen;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = R6Constants.MOD_ID)
public class ModDataGenRegistry {
    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event) {
            event.getGenerator().addProvider(event.includeClient(), new ModItemModelProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));

    }
}
