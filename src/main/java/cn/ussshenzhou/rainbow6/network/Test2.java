package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.t88.network.AbstractNetworkPacket;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@NetPacket(packetName = "r6ms:test2")
public class Test2 extends AbstractNetworkPacket {
    public Test2() {
    }

    @Decoder
    public static Test2 decode(FriendlyByteBuf buf) {
        return new Test2();
    }

    @Encoder
    public void write2(FriendlyByteBuf buf){

    }

    @Consumer
    public void handler2(Supplier<NetworkEvent.Context> context){
    }
}
