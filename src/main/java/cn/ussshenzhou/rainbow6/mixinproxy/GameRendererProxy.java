package cn.ussshenzhou.rainbow6.mixinproxy;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface GameRendererProxy {
    default void needScreenShot() {

    }

    default NativeImage getScreenShot$r6ms() {
        return null;
    }

    default void clearScreenShot(){
    }
}
