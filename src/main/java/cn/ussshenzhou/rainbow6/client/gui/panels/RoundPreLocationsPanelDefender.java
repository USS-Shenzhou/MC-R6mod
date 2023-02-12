package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.util.MapHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.DynamicTexture;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
public class RoundPreLocationsPanelDefender extends RoundPreLocationsPanel {
    volatile ArrayList<DynamicTextureWithMapData> map = null;

    public RoundPreLocationsPanelDefender() {
        super();
        CompletableFuture.runAsync(() -> {
            map = MapHelper.generateMap();
            ScreenManager.playerInfoBarHud.getTimer().start();
        });
    }

    @Override
    public boolean isReady() {
        return map != null;
    }

    @Override
    public void renderBackground(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (map != null) {
            //RenderSystem.setShaderTexture(0, map.getId());
            //blit(pPoseStack, 0, 0, width, height, 0, 0, map.getPixels().getWidth(), map.getPixels().getHeight(), map.getPixels().getWidth(), map.getPixels().getHeight());
        }
        super.renderBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

}
