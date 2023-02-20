package cn.ussshenzhou.rainbow6.client.gui.panels;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.screens.RoundPrepareScreen;
import cn.ussshenzhou.rainbow6.client.gui.widgets.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.util.MapHelper;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.Vec2i;
import cn.ussshenzhou.t88.gui.widegt.TButton;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class RoundPreLocationsPanelAttacker extends RoundPreLocationsPanel {
    @Nullable
    volatile DynamicTextureWithMapData map = null;
    LinkedHashMap<SpawnPosButton, SpawnPosSign> spawnPosPairs = new LinkedHashMap<>();
    private boolean noneSelected = true;

    public RoundPreLocationsPanelAttacker() {
        super(new TLabel(new TranslatableComponent("gui.r6ms.round_prepare.team_spawn_location")));
        CompletableFuture.runAsync(() -> {
            map = MapHelper.generateMap().get(0);
            ScreenManager.playerInfoBarHud.getTimer().start();
            Minecraft.getInstance().execute(this::layout);
        });
        initSpawnPoint();

    }

    private void initSpawnPoint() {
        ArrayList<Map.SpawnPos> spawnPositions = ClientMatch.getMap().getSpawnPositions();
        for (Map.SpawnPos pos : spawnPositions) {
            SpawnPosButton button = new SpawnPosButton(pos);
            SpawnPosSign sign = new SpawnPosSign(pos, button);
            this.addAll(button, sign);
            spawnPosPairs.put(button, sign);
        }
    }

    private void setSelectedPos(SpawnPosButton spawnPosButton) {
        spawnPosPairs.forEach((button, sign) -> {
            button.setSelected(false);
            sign.setSelected(false);
        });
        spawnPosButton.setSelected(true);
        spawnPosPairs.get(spawnPosButton).setSelected(true);
        //TODO actually select
        if (noneSelected) {
            RoundPrepareScreen screen = (RoundPrepareScreen) this.getParentScreen();
            screen.setButtonSelectedAndPanelVisible(screen.getOperatorsButton(), screen.getOperatorsPanel());
            noneSelected = false;
        }
    }

    @Override
    public void layout() {
        if (map != null) {
            SpawnPosButton[] buttons = spawnPosPairs.keySet().toArray(new SpawnPosButton[0]);
            for (int i = 0; i < spawnPosPairs.size(); i++) {
                buttons[i].setBounds(12, 65 + (23 - 4) * i, 119, 23);
                SpawnPosSign sign = spawnPosPairs.get(buttons[i]);
                Vec2i v = map.getScreenXY(sign.pos.getSpawnPosPos().getX(), sign.pos.getSpawnPosPos().getZ());
                sign.setBounds(v.x - 9, v.y - 9, 18, 18);
            }
        }
        super.layout();
    }

    @Override
    public boolean isReady() {
        return map != null;
    }

    @Override
    public void renderBackground(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (map != null) {
            RenderSystem.setShaderTexture(0, map.getId());
            blit(pPoseStack, 0, 0, width, height, 0, 0, map.getPixels().getWidth(), map.getPixels().getHeight(), map.getPixels().getWidth(), map.getPixels().getHeight());
        }
        super.renderBackground(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private class SpawnPosButton extends FocusSensitiveImageSelectButton {

        public SpawnPosButton(Map.SpawnPos pos) {
            super(new TranslatableComponent(Minecraft.getInstance().options.forceUnicodeFont ? " ◎ " : " ⭘ " + pos.getSpawnPosName()),
                    pButton -> setSelectedPos((SpawnPosButton) ((TButton) pButton).getParent()),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_unhovered.png"),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button23_hovered.png"));
            this.setPadding(R6Constants.PADDING_STD);
            this.getBackgroundImage().setAlpha(0.5f);
            this.getBackgroundImage().setImageFit(ImageFit.STRETCH);
            this.getText().setFontSize(R6Constants.FONT_SMALL_3);
        }

        @Override
        public void tickT() {
            spawnPosPairs.get(this).setRelatedHover(this.button.isHoveredOrFocused());
            super.tickT();
        }
    }

    public class SpawnPosSign extends FocusSensitiveImageSelectButton {
        private final Map.SpawnPos pos;
        private boolean relatedHover = false;

        public SpawnPosSign(Map.SpawnPos pos, SpawnPosButton spawnPosButton) {
            super(new TextComponent(""),
                    pButton -> spawnPosButton.getButton().onPress(),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/spawn_pos18_unselected12.png"),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/spawn_pos18_selected.png"));
            this.setPadding(3);
            this.pos = pos;
            this.remove(this.button);
            this.button = new TButton(new TextComponent(""), pButton -> setSelectedPos(spawnPosButton)) {
                @Override
                public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
                    return;
                }

                @Override
                public void onPress() {
                    super.onPress();
                    setSelected(true);
                }

                @Override
                public boolean isHoveredOrFocused() {
                    return super.isHoveredOrFocused() || isSelected() || relatedHover;
                }
            };
            this.add(this.button);
        }

        public boolean isRelatedHover() {
            return relatedHover;
        }

        public void setRelatedHover(boolean relatedHover) {
            this.relatedHover = relatedHover;
        }
    }
}