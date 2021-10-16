package com.ussshenzhou.rainbow6.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ussshenzhou.rainbow6.network.MatchMakingPack;
import com.ussshenzhou.rainbow6.network.MatchMakingPackSend;
import com.ussshenzhou.rainbow6.util.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;


/**
 * @author USS_Shenzhou
 */
public class R6MainMenuScreen extends Screen {
    public R6MainMenuScreen(ITextComponent titleIn) {
        super(titleIn);
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
    }

    public final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("rainbow6:textures/gui/mainmenu_background.png");
    double scaleFactorX = Minecraft.getInstance().getMainWindow().getScaledWidth() / 1920.0;
    double scaleFactorY = Minecraft.getInstance().getMainWindow().getScaledHeight() / 1080.0;
    R6Button playButton;
    R6Button clientTestButton;
    //Boolean Test = false;

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        TranslationTextComponent playText = new TranslationTextComponent("gui.rainbow6.playbutton");
        TranslationTextComponent exitText = new TranslationTextComponent("gui.rainbow6.exitbutton");
        playButton = new R6Button((int) (this.width / 10),
                (int) (this.height / 2.8),
                (int) (100),
                (int) (20),
                InGameClientProperties.getIsWaitingMatchMaking() ? exitText : playText,
                (playButton) -> {
                    //switch text
                    if (InGameClientProperties.getIsWaitingMatchMaking()) {
                        MatchMakingPackSend.channel.sendToServer(new MatchMakingPack("exit", Minecraft.getInstance().player.getUniqueID()));
                        playButton.setMessage(playText);
                        InGameClientProperties.setIsWaitingMatchMaking(false);
                    } else {
                        InGameClientTimeCount.resetMatchMakingTime();
                        MatchMakingPackSend.channel.sendToServer(new MatchMakingPack("join", Minecraft.getInstance().player.getUniqueID()));
                        playButton.setMessage(exitText);
                        InGameClientProperties.setIsWaitingMatchMaking(true);
                    }
                });
        ////////////////////////////////////////////////////////
        clientTestButton = new R6Button(
                (int) (this.width / 10),
                (int) (this.height / 2),
                (int) (100),
                (int) (20),
                new TranslationTextComponent("test"),
                (button) -> {
                    //minecraft.player.addItemStackToInventory(new ItemStack(Items.STONE,5,null));
                    //this.Test = !this.Test;
                    //minecraft.world.playSound(minecraft.player.getPosX(),minecraft.player.getPosY(),minecraft.player.getPosZ(),ModSounds.MATCHMADE,SoundCategory.PLAYERS,1.0f,1.0f,false);
                    //ClientMatch.setIsBlueAttacker(!ClientMatch.getIsBlueAttacker());
                });
        playButton.setAlpha(1f);
        this.addButton(playButton);
        this.addButton(clientTestButton);
        super.init();
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        matrixStack.push();
        matrixStack.scale((float) scaleFactorX, (float) scaleFactorY, 1f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);
        blit(matrixStack, 0, 0, 0, 0, 1920, 1080, 1920, 1080);
        matrixStack.pop();
        this.playButton.render(matrixStack, mouseX, mouseY, partialTicks);
        this.clientTestButton.render(matrixStack,mouseX,mouseY,partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 54 && modifiers == 0x4) {
            this.closeScreen();
            return true;
        } else{
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}