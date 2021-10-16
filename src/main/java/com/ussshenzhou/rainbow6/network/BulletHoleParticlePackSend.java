package com.ussshenzhou.rainbow6.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author USS_Shenzhou
 */
public class BulletHoleParticlePackSend {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("rainbow6", "first_networking"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.registerMessage(
                nextID(),
                BulletHoleParticlePack.class,
                BulletHoleParticlePack::toBytes,
                BulletHoleParticlePack::new,
                BulletHoleParticlePack::handler
        );
    }
}
