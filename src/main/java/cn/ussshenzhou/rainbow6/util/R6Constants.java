package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.t88.gui.util.Vec2i;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class R6Constants {
    public static final String MOD_ID = "r6ms";

    public static final ResourceLocation PLACEHOLDER_IMAGE = new ResourceLocation(MOD_ID, "textures/gui/placeholder.png");

    public static final int PADDING_STD = 4;
    public static final int PADDING_SMALL = 2;

    public static final float FONT_SMALL = 5.249f;

    public static final int BLUE_STD_ARGB = 0xff1985e2;
    public static final int BLUE_STD_RGB = 0x1985e2;
    public static final int ORANGE_STD_ARGB = 0xfff9952b;
    public static final int ORANGE_STD_RGB = 0xf9952b;

    /**
     * 1 m = 8 pixel
     */
    public static final Vec2i TINY_MAP_PLAYABLE = new Vec2i(135,90);
    public static final Vec2i TINY_MAP_COMPLETE = new Vec2i(240,135);
    /**
     * 1 m = 6 pixel
     */
    public static final Vec2i SMALL_MAP_PLAYABLE = new Vec2i(180,120);
    public static final Vec2i SMALL_MAP_COMPLETE = new Vec2i(320,180);
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
