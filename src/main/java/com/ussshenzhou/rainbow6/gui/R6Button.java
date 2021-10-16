package com.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

/**
 * @author USS_Shenzhou
 */
public class R6Button extends Button {
    public R6Button(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    public R6Button(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }
    public final ResourceLocation OPERATORS_LOCATION = new ResourceLocation("rainbow6:textures/gui/operators.png");
    private Boolean pressable = true;
    @Override
    public void playDownSound(SoundHandler handler) {
        handler.play(SimpleSound.master(ModSounds.R6BUTTON_DOWN, 1.0F));
    }

    @Override
    public void onPress() {
        super.onPress();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (pressable){
            return super.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    public void setPressable(Boolean pressable) {
        this.pressable = pressable;
        if (pressable){
            this.setFGColor(0xffffff);
        } else {
            this.setFGColor(0xaaaaaa);
        }
    }
}
