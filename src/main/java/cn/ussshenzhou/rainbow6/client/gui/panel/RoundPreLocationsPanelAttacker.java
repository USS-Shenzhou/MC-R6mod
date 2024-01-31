package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.gui.GuiUtil;
import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.screen.RoundPrepareScreen;
import cn.ussshenzhou.t88.gui.advanced.TFocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.network.onlyto.server.ChooseAttackerSpawnPacket;
import cn.ussshenzhou.rainbow6.util.MapTopViewHelper;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import org.joml.Vector2i;
import cn.ussshenzhou.t88.gui.widegt.TButton;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
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
    private boolean noneSelectedBefore = true;

    public RoundPreLocationsPanelAttacker() {
        super(new TLabel(Component.translatable("gui.r6ms.round_prepare.team_spawn_location")));
        CompletableFuture.runAsync(() -> {
            map = MapTopViewHelper.generateMap().get(0);
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
        NetworkHelper.sendToServer(new ChooseAttackerSpawnPacket(spawnPosPairs.keySet().stream().toList().indexOf(spawnPosButton)));
        if (noneSelectedBefore) {
            RoundPrepareScreen screen = (RoundPrepareScreen) this.getParentScreen();
            screen.setButtonSelectedAndPanelVisible(screen.getOperatorsButton(), screen.getOperatorsPanel());
            noneSelectedBefore = false;
        }
    }

    @Override
    public void layout() {
        if (map != null) {
            SpawnPosButton[] buttons = spawnPosPairs.keySet().toArray(new SpawnPosButton[0]);
            for (int i = 0; i < spawnPosPairs.size(); i++) {
                buttons[i].setBounds(12, 65 + (23 - 4) * i, GuiUtil.button23PreferredSize());
                SpawnPosSign sign = spawnPosPairs.get(buttons[i]);
                Vector2i v = map.getScreenXY(sign.pos.getSpawnPosPos().getX(), sign.pos.getSpawnPosPos().getZ());
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
    public void renderBackground(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (map != null) {
            blitById(graphics, map.getId(), 0, 0, width, height, 0, 0, map.getPixels().getWidth(), map.getPixels().getHeight(), map.getPixels().getWidth(), map.getPixels().getHeight());

        }
        super.renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
    }

    private class SpawnPosButton extends TFocusSensitiveImageSelectButton {

        public SpawnPosButton(Map.SpawnPos pos) {
            super(Component.translatable(Minecraft.getInstance().options.forceUnicodeFont().get() ? " ◎ " : " ⭘ " + pos.getSpawnPosName()),
                    pButton -> setSelectedPos((SpawnPosButton) ((TButton) pButton).getParent()),
                    GuiUtil.buttonStdUnhovered(),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button23_hovered.png"));
            this.setPadding(R6Constants.PADDING_STD);
            this.getBackgroundImage().setAlpha(0.5f);
            this.getBackgroundImage().setImageFit(ImageFit.STRETCH);
            this.getText().setFontSize(R6Constants.FONT_SMALL_3).setHorizontalAlignment(HorizontalAlignment.LEFT);
        }

        @Override
        public void tickT() {
            spawnPosPairs.get(this).setRelatedHover(this.button.isHoveredOrFocused());
            super.tickT();
        }
    }

    public class SpawnPosSign extends TFocusSensitiveImageSelectButton {
        private final Map.SpawnPos pos;
        private boolean relatedHover = false;

        public SpawnPosSign(Map.SpawnPos pos, SpawnPosButton spawnPosButton) {
            super(Component.literal(""),
                    pButton -> spawnPosButton.getButton().onPress(),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/spawn_pos18_unselected12.png"),
                    new ResourceLocation(R6Constants.MOD_ID, "textures/gui/spawn_pos18_selected.png"));
            this.setPadding(3);
            this.pos = pos;
            this.remove(this.button);
            this.button = new TButton(Component.literal(""), pButton -> setSelectedPos(spawnPosButton)) {
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
