package cn.ussshenzhou.rainbow6.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author USS_Shenzhou
 */
public class MatchMakingPackSend {
    public static SimpleChannel channel;
    public static String VERSION = "1.0";
    private static int id = 0;

    public static int nextId(){
        return id++;
    }

    public static void registerMessage(){
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("rainbow6","matchmakingpack"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        channel.messageBuilder(MatchMakingPack.class,nextId())
                .encoder(MatchMakingPack::toBytes)
                .decoder(MatchMakingPack::new)
                .consumer(MatchMakingPack::handler)
                .add();
    }
}
