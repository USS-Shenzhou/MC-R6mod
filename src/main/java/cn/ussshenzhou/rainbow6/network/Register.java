package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.util.TeamColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar("r6ms");

        registrar.play(TestPacket.ID, TestPacket::new,
                handler -> handler
                        .client((payload, context) -> context.workHandler().submitAsync(() -> payload.clientHandler(context)))
                        .server((payload, context) -> context.workHandler().submitAsync(() -> payload.serverHandler(context)))
        );


        PacketDistributor.PLAYER.noArg().send(new TestPacket(TeamColor.BLUE));
        PacketDistributor.SERVER.noArg().send(new TestPacket(TeamColor.BLUE));

    }
}
