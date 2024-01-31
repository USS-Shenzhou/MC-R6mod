package cn.ussshenzhou.rainbow6.client.gui.widget;

import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class HoverSensitiveImageButton1 extends THoverSensitiveImageButton implements NarratableEntry {

    public HoverSensitiveImageButton1(Component text1, Button.OnPress onPress, ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationFocused) {
        super(text1, onPress, backgroundImageLocation, backgroundImageLocationFocused);
    }

    @Override
    public NarrationPriority narrationPriority() {
        if (this.getButton().isHoveredOrFocused()) {
            return NarrationPriority.HOVERED;
        } else {
            return NarratableEntry.NarrationPriority.NONE;
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
