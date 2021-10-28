package cn.ussshenzhou.rainbow6.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author USS_Shenzhou
 */
public class EE1DPackSend {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("rainbow6", "ee1d"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.registerMessage(
                nextID(),
                EE1DPack.class,
                EE1DPack::toBytes,
                EE1DPack::new,
                EE1DPack::handler
        );
    }
}
