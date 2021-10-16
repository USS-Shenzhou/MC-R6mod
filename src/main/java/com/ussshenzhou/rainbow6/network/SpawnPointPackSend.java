package com.ussshenzhou.rainbow6.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author USS_Shenzhou
 */
public class SpawnPointPackSend {
    public static SimpleChannel channel;
    public static String VERSION = "1.0";
    private static int id = 0;

    public static int nextId(){
        return id++;
    }

    public static void registerMessage(){
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("rainbow6","spawnpointpack"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        channel.messageBuilder(SpawnPointPack.class,nextId())
                .encoder(SpawnPointPack::toBytes)
                .decoder(SpawnPointPack::new)
                .consumer(SpawnPointPack::handler)
                .add();
    }
}
