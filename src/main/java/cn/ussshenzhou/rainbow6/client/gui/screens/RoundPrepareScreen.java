package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.client.gui.MapHelper;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.widgets.PlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
public class RoundPrepareScreen extends AbstractR6Screen {
    private final TLabel header = new TLabel(new TextComponent(ClientMatch.getMap().getName() + "  "));
    DynamicTexture map = null;
    private final TImage backgroundMaskL = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_left.png"));
    private final TImage backgroundMaskR = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_prepare_background_mask_right.png"));

    public RoundPrepareScreen() {
        super("RoundPrepareScreen");
        //---dev---
        ScreenManager.playerInfoBarHud = new PlayerInfoBarHud(30);
        ScreenManager.playerInfoBarHud.getTimer().tickT();
        HudManager.add(ScreenManager.playerInfoBarHud);
        CompletableFuture.runAsync(() -> {
            map = MapHelper.generateMap().get(0);
            ScreenManager.playerInfoBarHud.getTimer().start();
        });
        //---dev---
        backgroundMaskL.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskL);
        backgroundMaskR.setImageFit(ImageFit.STRETCH);
        this.add(backgroundMaskR);
        header.setBackground(0x80000000);
        header.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        this.add(header);
    }

    @Override
    public void layout() {
        header.setBounds(0, 0, width, 18);
        backgroundMaskL.setBounds(0, 0, 50, height);
        backgroundMaskR.setBounds(width - 50, 0, 50, height);
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (map != null) {
            RenderSystem.setShaderTexture(0, map.getId());
            blit(pPoseStack, 0, 0, width, height, 0, 0, map.getPixels().getWidth(), map.getPixels().getHeight(), map.getPixels().getWidth(), map.getPixels().getHeight());
        }
    }
}
