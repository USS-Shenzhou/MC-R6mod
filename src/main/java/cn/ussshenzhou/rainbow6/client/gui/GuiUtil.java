package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2i;

/**
 * @author USS_Shenzhou
 */
public class GuiUtil {


    private static final ResourceLocation BUTTON_STD_UNHOVERED = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png");

    public static ResourceLocation buttonStdUnhovered() {
        return BUTTON_STD_UNHOVERED;
    }

    private static final Vector2i BUTTON_23_PREFERRED_SIZE = new Vector2i(119, 23);

    public static Vector2i button23PreferredSize() {
        return new Vector2i(BUTTON_23_PREFERRED_SIZE);
    }

    public static THoverSensitiveImageButton defaultInit(THoverSensitiveImageButton button) {
        button.setPadding(R6Constants.PADDING_STD)
                .getBackgroundImage().setImageFit(ImageFit.STRETCH);
        return button;
    }
}
