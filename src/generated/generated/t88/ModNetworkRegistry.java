// Automatically generated by cn.ussshenzhou.t88.network.NetworkAnnotationProcessor
package cn.ussshenzhou.rainbow6.network.generated;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import cn.ussshenzhou.t88.network.PacketProxy;

import cn.ussshenzhou.rainbow6.network.MatchMakerChange;
import cn.ussshenzhou.rainbow6.network.RoundPrepareTopView;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModNetworkRegistry {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            SimpleChannel channelMatchMakerChange = NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("MatchMakerChange"),
                    () -> "1.0",
                    (version) -> version.equals("1.0"),
                    (version) -> version.equals("1.0")
            );

            channelMatchMakerChange.messageBuilder(MatchMakerChange.class, getId())
                    .encoder(MatchMakerChange::write)
                    .decoder(MatchMakerChange::new)
                    .consumer(MatchMakerChange::handler)
                    .add();
            PacketProxy.addChannel("matchmakerchange", channelMatchMakerChange);

            SimpleChannel channelRoundPrepareTopView = NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("RoundPrepareTopView"),
                    () -> "1.0",
                    (version) -> version.equals("1.0"),
                    (version) -> version.equals("1.0")
            );

            channelRoundPrepareTopView.messageBuilder(RoundPrepareTopView.class, getId())
                    .encoder(RoundPrepareTopView::write)
                    .decoder(RoundPrepareTopView::new)
                    .consumer(RoundPrepareTopView::handler)
                    .add();
            PacketProxy.addChannel("roundpreparetopview", channelRoundPrepareTopView);

        });
    }

    static int id = 0;

    public static int getId() {
        return id++;
    }
}
