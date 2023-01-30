package cn.ussshenzhou.rainbow6.client.gui.screens;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class MatchMapSceneScreen extends TScreen {
    private final TLabel bomb = new TLabel(new TranslatableComponent("gui.r6ms.match-map-scene.bomb"));
    private final TLabel quickMatch = new TLabel(new TranslatableComponent("gui.r6ms.match-map-scene.quick-match"));
    private final TImage background = new TImage(ClientMatch.getTeamColor() == TeamColor.BLUE ?
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/match_map_scene_blue.png")
            : new ResourceLocation(R6Constants.MOD_ID, "textures/gui/match_map_scene_orange.png")
    );
    private final TLabel yourTeam = new TLabel(new TranslatableComponent("gui.r6ms.match-map-scene.your-team"));
    private final TLabel enemy = new TLabel(new TranslatableComponent("gui.r6ms.match-map-scene.enemy"));
    //private final TImage attackerIcon
    //private final TImage defenderIcon
    private final TLabel mapName = new TLabel(new TextComponent(ClientMatch.mapName()));

    public MatchMapSceneScreen() {
        super(new TextComponent("MatchMapSceneScreen"));
        bomb.setHorizontalAlignment(HorizontalAlignment.CENTER);
        bomb.setFontSize(21);
        this.add(bomb);
        quickMatch.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(quickMatch);
        this.add(background);
        yourTeam.setFontSize(10);
        yourTeam.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(yourTeam);
        enemy.setFontSize(9);
        enemy.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(enemy);
        mapName.setBackground(0x60000000);
        mapName.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(mapName);
    }

    @Override
    public void layout() {
        bomb.setBounds(0, height / 2, width, bomb.getPreferredSize().y);
        LayoutHelper.BBottomOfA(quickMatch, 0, bomb);
        background.setBounds((width - 222) / 2, height / 2 - 25 - 7, 222, 25);
        yourTeam.setBounds(
                background.getX() + (82 - yourTeam.getPreferredSize().x) / 2,
                background.getY() + (25 - yourTeam.getPreferredSize().y) / 2,
                yourTeam.getPreferredSize()
        );
        enemy.setBounds(
                width / 2 + 22 + (60 - enemy.getPreferredSize().x) / 2,
                background.getY() + 3 + (19 - enemy.getPreferredSize().y) / 2,
                enemy.getPreferredSize()
        );
        mapName.setBounds(width - mapName.getPreferredSize().x - 20, 33, mapName.getPreferredSize().x + 20, 21);
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
    }
}
