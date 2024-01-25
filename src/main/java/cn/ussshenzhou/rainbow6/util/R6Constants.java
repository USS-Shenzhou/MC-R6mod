package cn.ussshenzhou.rainbow6.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.util.LogicalSidedProvider;
import net.neoforged.neoforge.gametest.GameTestHooks;

import java.lang.management.ManagementFactory;

/**
 * @author USS_Shenzhou
 */
public class R6Constants {
    public static final String MOD_ID = "r6ms";
    public static final boolean TEST = GameTestHooks.isGametestEnabled();
    public static final boolean IS_ONLINE_SERVER = getIsOnLineServer();

    private static boolean getIsOnLineServer() {
        try {
            MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
            if (minecraftServer instanceof DedicatedServer server) {
                DedicatedServerProperties properties = server.getProperties();
                if (properties.onlineMode) {
                    //check authlib-injector
                    for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                        if (arg.startsWith("-javaagent:")) {
                            String agent = arg.substring("-javaagent:".length());
                            if (agent.contains("authlib-injector.jar")) {
                                if (!"https://authserver.mojang.com".equals(agent.split("=")[1])) {
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                    //MultiLogin is allowed.
                }
            }
        } catch (NullPointerException ignored) {
            /*try {
                LogUtils.getLogger().debug("Client Test: {}", Minecraft.getInstance().gameDirectory);
            } catch (Exception ignored1) {
                return true;
            }*/
        }
        return false;
    }

    public static final ResourceLocation PLACEHOLDER_IMAGE = new ResourceLocation(MOD_ID, "textures/gui/placeholder.png");
    public static final ResourceLocation STD_BACKGROUND = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/std_background_full_screen.png");

    public static final int PADDING_STD = 4;
    public static final int PADDING_SMALL = 2;
    public static final int PADDING_TINY = 1;

    public static final float FONT_SMALL_3 = 5.249f;
    public static final float FONT_TINY_2 = 3.49f;

    public static final int BLUE_STD_ARGB = 0xff1985e2;
    public static final int BLUE_STD_RGB = 0x1985e2;
    public static final int ORANGE_STD_ARGB = 0xfff9952b;
    public static final int ORANGE_STD_RGB = 0xf9952b;

    public static final int CYAN_STD_RGB = 0x0bfff0;
    public static final int CYAN_STD_ARGB = 0xff0bfff0;

    public static final String YELLOW = "§g";
}
