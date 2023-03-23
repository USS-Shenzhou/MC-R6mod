package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.t88.gui.util.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.gametest.ForgeGameTestHooks;

import java.lang.management.ManagementFactory;

/**
 * @author USS_Shenzhou
 */
public class R6Constants {
    public static final String MOD_ID = "r6ms";
    public static final boolean TEST = ForgeGameTestHooks.isGametestEnabled();
    public static final boolean IS_ONLINE_SERVER = getIsOnLineServer();

    private static boolean getIsOnLineServer() {
        MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        if (minecraftServer instanceof DedicatedServer server) {
            DedicatedServerProperties properties = server.getProperties();
            if (properties.onlineMode) {
                //check authlib-injector
                for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                    if (arg.startsWith("-javaagent:")) {
                        String agent = arg.substring("-javaagent:".length());
                        if (agent.contains("authlib-injector.jar")) {
                            if (!"https://authserver.mojang.com".equals(agent.split("=")[1])){
                                return false;
                            }
                        }
                    }
                }
                return true;
                //MultiLogin is allowed.
            }
        }
        return false;
    }

    public static final ResourceLocation PLACEHOLDER_IMAGE = new ResourceLocation(MOD_ID, "textures/gui/placeholder.png");

    public static final int PADDING_STD = 4;
    public static final int PADDING_SMALL = 2;

    public static final float FONT_SMALL_3 = 5.249f;
    public static final float FONT_TINY_2 = 3.49f;

    public static final int BLUE_STD_ARGB = 0xff1985e2;
    public static final int BLUE_STD_RGB = 0x1985e2;
    public static final int ORANGE_STD_ARGB = 0xfff9952b;
    public static final int ORANGE_STD_RGB = 0xf9952b;

    public static final int CYAN_STD_RGB = 0x0bfff0;
    public static final int CYAN_STD_ARGB = 0xff0bfff0;

    public static final String YELLOW = "§g";

    /**
     * 1 m = 8 pixel
     */
    public static final Vec2i TINY_MAP_PLAYABLE = new Vec2i(135, 90);
    public static final Vec2i TINY_MAP_COMPLETE = new Vec2i(240, 135);
    /**
     * 1 m = 6 pixel
     */
    public static final Vec2i SMALL_MAP_PLAYABLE = new Vec2i(180, 120);
    public static final Vec2i SMALL_MAP_COMPLETE = new Vec2i(320, 180);
    /**
     * 1 m = 4 pixel
     */
    public static final Vec2i MID_MAP_PLAYABLE = new Vec2i(270, 180);
    public static final Vec2i MID_MAP_COMPLETE = new Vec2i(480, 270);
    /**
     * 1 m = 2 pixel
     */
    public static final Vec2i BIG_MAP_PLAYABLE = new Vec2i(540, 360);
    public static final Vec2i BIG_MAP_COMPLETE = new Vec2i(960, 540);
    /**
     * 1 m = 1 pixel
     */
    public static final Vec2i HUGE_MAP_PLAYABLE = new Vec2i(1080, 720);
    public static final Vec2i HUGE_MAP_COMPLETE = new Vec2i(1920, 1080);
}
