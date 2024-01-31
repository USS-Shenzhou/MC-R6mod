package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.gui.GuiUtil;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.t88.gui.advanced.TFocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.util.MapTopViewHelper;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import org.joml.Vector2i;
import cn.ussshenzhou.t88.gui.widegt.TButton;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class RoundPreLocationsPanelDefender extends RoundPreLocationsPanel {
    volatile ArrayList<DynamicTextureWithMapData> map = null;
    LinkedHashMap<BombSiteButton, TImage[]> bombSitePairs = new LinkedHashMap<>();
    //Do not need now
    //private boolean noneSelected = true;

    public RoundPreLocationsPanelDefender() {
        super(new TLabel(Component.translatable("gui.r6ms.round_prepare.bomb_location")));
        CompletableFuture.runAsync(() -> {
            map = MapTopViewHelper.generateMap();
            ScreenManager.playerInfoBarHud.getTimer().start();
        });
    }

    private void initBombSites() {
        //TODO what if client get RoundStartPacket before NotifyBombSitePacket?
        //-----casual - bombSite assigned by server-----
        int bombSiteIndex = ClientMatch.getBombSiteIndex();
        int i = 0;
        BombSiteButton assigned = null;

        ArrayList<Map.BombSite> bombSites = ClientMatch.getMap().getBombSites();
        for (Map.BombSite site : bombSites) {
            BombSiteButton button = new BombSiteButton(site);
            TImage[] signs = {newBombSiteSign("a"), newBombSiteSign("b")};
            signs[0].setVisibleT(false);
            signs[1].setVisibleT(false);
            this.add(button);
            this.addAll(signs);
            bombSitePairs.put(button, signs);
            //-----casual - bombSite assigned by server-----
            button.disable();
            if (i == bombSiteIndex) {
                assigned = button;
            }
            i++;
        }
        //-----casual - bombSite assigned by server-----
        if (assigned == null) {
            //TODO something wrong
            return;
        }
        setSelectedSite(assigned);
    }

    private void setSelectedSite(BombSiteButton siteButton) {
        bombSitePairs.forEach((button, sign) -> {
            button.setSelected(false);
            Arrays.stream(sign).forEach(tImage -> tImage.setVisibleT(false));
        });
        siteButton.setSelected(true);
        Arrays.stream(bombSitePairs.get(siteButton)).forEach(tImage -> {
            tImage.setVisibleT(true);
        });
        //TODO actually select - do not need now, because bomb site is assigned by server randomly.

        //Do not need now
        /*if (noneSelected) {
            RoundPrepareScreen screen = (RoundPrepareScreen) this.getParentScreen();
            screen.setButtonSelectedAndPanelVisible(screen.getOperatorsButton(), screen.getOperatorsPanel());
            noneSelected = false;
        }*/
    }

    @Override
    public void layout() {
        if (map != null) {
            BombSiteButton[] buttons = bombSitePairs.keySet().toArray(new BombSiteButton[0]);
            for (int i = 0; i < bombSitePairs.size(); i++) {
                BombSiteButton b = buttons[i];
                b.setBounds(12, 65 + (23 - 4) * i, GuiUtil.button23PreferredSize());
                Vector2i v1 = map.get(0).getScreenXY(b.site.getSubSite1Pos().getX(), b.site.getSubSite1Pos().getZ());
                bombSitePairs.get(b)[0].setBounds(v1.x - 4, v1.y - 7, 8, 14);
                Vector2i v2 = map.get(0).getScreenXY(b.site.getSubSite2Pos().getX(), b.site.getSubSite2Pos().getZ());
                bombSitePairs.get(b)[1].setBounds(v2.x - 4, v2.y - 7, 8, 14);
            }
        }
        super.layout();
    }

    @Override
    public boolean isReady() {
        return map != null;
    }

    int mapIndex = 0;
    boolean fresh = true;

    @Override
    public void tickT() {
        if (fresh) {
            initBombSites();
            fresh = false;
        }

        BombSiteButton[] buttons = bombSitePairs.keySet().toArray(new BombSiteButton[0]);
        bombSitePairs.values().forEach(tImages -> {
            tImages[0].setVisibleT(false);
            tImages[1].setVisibleT(false);
        });
        for (int i = 0; i < bombSitePairs.size(); i++) {
            if (buttons[i].getButton().isHoveredOrFocused()) {
                Arrays.stream(bombSitePairs.get(buttons[i])).forEach(tImage -> tImage.setVisibleT(true));
                mapIndex = i + 1;
                break;
            }
            mapIndex = 0;
        }
        super.tickT();
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (map != null) {
            DynamicTextureWithMapData m = map.get(mapIndex);
            blitById(graphics, m.getId(), 0, 0, width, height, 0, 0, m.getPixels().getWidth(), m.getPixels().getHeight(), m.getPixels().getWidth(), m.getPixels().getHeight());
        }
        super.renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
    }

    private TImage newBombSiteSign(String aOrb) {
        return new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/bomb_site14_" + aOrb + ".png"));
    }

    public class BombSiteButton extends TFocusSensitiveImageSelectButton {
        private boolean enable = true;
        private final TLabel prefix = new TLabel(Component.literal(Minecraft.getInstance().options.forceUnicodeFont().get() ? " ◎ " : " ⭘ "));
        private final Map.BombSite site;

        public BombSiteButton(Map.BombSite site) {
            super(Component.translatable("    " + site.getSubSite1Name() + "\n    " + site.getSubSite2Name()),
                    pButton -> {
                    },
                    GuiUtil.buttonStdUnhovered(),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button23_hovered.png"));
            prefix.setFontSize(R6Constants.FONT_SMALL_3);
            this.add(prefix);
            this.remove(button);
            this.button = new TButton(Component.literal(""), pButton -> setSelectedSite(this)) {
                @Override
                public void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
                    return;
                }

                @Override
                public void onPress() {
                    super.onPress();
                    setSelected(true);
                }

                @Override
                public boolean isHoveredOrFocused() {
                    return enable && (super.isHoveredOrFocused() || isSelected());
                }

                @Override
                public boolean isInRange(double pMouseX, double pMouseY) {
                    return enable && super.isInRange(pMouseX, pMouseY);
                }

                @Override
                public boolean isInRange(double pMouseX, double pMouseY, double xPadding, double yPadding) {
                    return enable && super.isInRange(pMouseX, pMouseY, xPadding, yPadding);
                }

                @Override
                public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
                    return enable && super.mouseClicked(pMouseX, pMouseY, pButton);
                }
            };
            this.add(button);
            this.setPadding(R6Constants.PADDING_STD);
            this.getBackgroundImage().setAlpha(0.5f);
            this.getBackgroundImage().setImageFit(ImageFit.STRETCH);
            this.getText()
                    .setFontSize(R6Constants.FONT_SMALL_3)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);
            this.site = site;
        }

        public void disable() {
            this.enable = false;
            this.backgroundImage.setImageLocation(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button23_disabled15.png"));
        }

        @Override
        public void layout() {
            super.layout();
            LayoutHelper.BSameAsA(prefix, this.text);
        }

        @Override
        protected void renderChildren(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            for (TWidget tWidget : children) {
                if (tWidget.isVisibleT()) {
                    if (tWidget == prefix && backgroundImageHovered.isVisibleT()) {
                        continue;
                    }
                    if (tWidget == text && backgroundImageHovered.isVisibleT()) {
                        renderText(graphics, pMouseX, pMouseY, pPartialTick);
                        continue;
                    }
                    if (tWidget == backgroundImageHovered) {
                        renderBgImageHovered(graphics, pMouseX, pMouseY, pPartialTick);
                        continue;
                    }
                    tWidget.render(graphics, pMouseX, pMouseY, pPartialTick);
                }
            }
        }

        @Override
        public void renderTextInternal(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick, float scale, float maxScale) {
            float compensationRelative = (1 - scale) / (maxScale - 1) * this.getPadding();
            var poseStack = graphics.pose();
            poseStack.pushPose();
            poseStack.translate(
                    (1 - scale) * text.getXT() + compensationRelative,
                    (1 - scale) * text.getYT() + compensationRelative,
                    0);
            poseStack.scale(scale, scale, 1);
            text.render(graphics, pMouseX, pMouseY, pPartialTick);
            prefix.render(graphics, pMouseX, pMouseY, pPartialTick);
            poseStack.popPose();
        }
    }
}
