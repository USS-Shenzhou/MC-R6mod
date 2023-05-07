package cn.ussshenzhou.rainbow6.datagen;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
