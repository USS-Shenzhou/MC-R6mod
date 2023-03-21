package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public class PreparationStagePacket {

    public PreparationStagePacket() {
    }

    @Decoder
    public PreparationStagePacket(FriendlyByteBuf buf) {
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler();
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {
        ClientMatch.preStage();
    }

    public void serverHandler() {

    }
}
