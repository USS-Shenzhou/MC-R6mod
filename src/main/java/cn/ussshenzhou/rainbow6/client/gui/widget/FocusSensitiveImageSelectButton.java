package cn.ussshenzhou.rainbow6.client.gui.widget;

import cn.ussshenzhou.t88.gui.widegt.TButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class FocusSensitiveImageSelectButton extends HoverSensitiveImageButton{
    private boolean selected = false;

    public FocusSensitiveImageSelectButton(Component text1, Button.OnPress onPress, ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationFocused) {
        super(text1, onPress, backgroundImageLocation, backgroundImageLocationFocused);
        this.remove(button);
        this.button = new TButton(Component.literal(""), onPress) {
            @Override
            public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
                return;
            }

            @Override
            public void onPress() {
                super.onPress();
                selected = true;
            }

            @Override
            public boolean isHoveredOrFocused() {
                return super.isHoveredOrFocused() || selected;
            }
        };
        this.add(button);
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
