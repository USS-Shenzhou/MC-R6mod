package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.client.gui.widgets.RoundPlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.TextComponent;

/**
 * @author USS_Shenzhou
 */
public class RoundPrepareScreen extends AbstractR6Screen {
    TLabel header = new TLabel(new TextComponent(ClientMatch.getMap().getName() + "  "));
    DynamicTexture map;

    public RoundPrepareScreen() {
        super("RoundPrepareScreen");
        //---dev---
        HudManager.add(new RoundPlayerInfoBarHud());
        //---dev---
        header.setBackground(0x80000000);
        header.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        this.add(header);
        initMap();
    }

    @Override
    public void layout() {
        header.setBounds(0, 0, width, 18);
        super.layout();
    }

    private void initMap() {
        //map = MapHelper.generateMap();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        //TODO
        /*RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1,1,1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();*/
        //map.bind();
        //blit(pPoseStack, 0, 0, width, height, 0, 0, map.getPixels().getWidth(), map.getPixels().getHeight(), map.getPixels().getWidth(), map.getPixels().getHeight());
    }
}
