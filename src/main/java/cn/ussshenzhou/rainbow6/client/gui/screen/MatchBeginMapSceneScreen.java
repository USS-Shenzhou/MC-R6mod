package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.IconHelper;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class MatchBeginMapSceneScreen extends AbstractR6Screen {
    private final TLabel bomb = new TLabel(Component.translatable("gui.r6ms.match_map_scene.bomb"));
    private final TLabel quickMatch = new TLabel(Component.translatable("gui.r6ms.match_map_scene.quick_match"));
    private final TImage background = new TImage(ClientMatch.getAllyTeamColor() == TeamColor.BLUE
            ? new ResourceLocation(R6Constants.MOD_ID, "textures/gui/match_begin_map_scene_blue.png")
            : new ResourceLocation(R6Constants.MOD_ID, "textures/gui/match_begin_map_scene_orange.png")
    );
    private final TLabel ally = new TLabel(Component.translatable("gui.r6ms.match_map_scene.your_team"));
    private final TLabel enemy = new TLabel(Component.translatable("gui.r6ms.match_map_scene.enemy"));
    private final TImage allyIcon = new TImage(IconHelper.getAllyIconWhite(19));
    private final TImage enemyIcon = new TImage(IconHelper.getEnemyIconWhite(13));
    private final TLabel mapName = new TLabel(Component.literal(ClientMatch.getMap().getName()));

    public MatchBeginMapSceneScreen() {
        super("MatchMapSceneScreen");
        bomb.setHorizontalAlignment(HorizontalAlignment.CENTER);
        bomb.setFontSize(21);
        this.add(bomb);
        quickMatch.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(quickMatch);
        this.add(background);
        ally.setFontSize(10);
        ally.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(ally);
        enemy.setFontSize(9);
        enemy.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(enemy);
        mapName.setBackground(0x60000000);
        mapName.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(mapName);
        this.add(allyIcon);
        this.add(enemyIcon);
    }

    @Override
    public void layout() {
        bomb.setBounds(0, height / 2, width, bomb.getPreferredSize().y);
        LayoutHelper.BBottomOfA(quickMatch, 0, bomb);
        background.setBounds((width - 222) / 2, height / 2 - 25 - 7, 222, 25);
        ally.setBounds(
                background.getXT() + (82 - ally.getPreferredSize().x) / 2,
                background.getYT() + (25 - ally.getPreferredSize().y) / 2,
                ally.getPreferredSize()
        );
        enemy.setBounds(
                width / 2 + 22 + (60 - enemy.getPreferredSize().x) / 2,
                background.getYT() + 3 + (19 - enemy.getPreferredSize().y) / 2,
                enemy.getPreferredSize()
        );
        mapName.setBounds(width - mapName.getPreferredSize().x - 20, 33, mapName.getPreferredSize().x + 20, 21);
        allyIcon.setBounds(background.getXT() + 87, background.getYT() + 3, 19, 19);
        enemyIcon.setBounds(background.getXT() + 115, background.getYT() + 6, 13, 13);
        super.layout();
    }

    @Override
    protected void renderBackGround(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
    }
}
