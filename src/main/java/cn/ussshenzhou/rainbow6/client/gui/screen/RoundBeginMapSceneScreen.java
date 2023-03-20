package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.IconHelper;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class RoundBeginMapSceneScreen extends AbstractR6Screen {
    private final TLabel round = new TLabel(Component.translatable("gui.r6ms.match_map_scene.round", ClientMatch.getCurrentRoundNumber()));
    private final TLabel side = new TLabel(Component.translatable(ClientMatch.getSide().getTranslateKey()));
    private final TLabel mapName = new TLabel(Component.literal(ClientMatch.getMap().getName()));
    private final TImage background = new TImage(ClientMatch.getTeamColor() == TeamColor.BLUE ?
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_begin_map_scene_blue.png")
            : new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_begin_map_scene_orange.png")
    );
    private final TLabel ally = new TLabel(Component.translatable("gui.r6ms.match_map_scene.your_team"));
    private final TLabel enemy = new TLabel(Component.translatable("gui.r6ms.match_map_scene.enemy"));
    private final TImage allyIcon = new TImage(IconHelper.getAllyIconWhite(15));
    private final TImage enemyIcon = new TImage(IconHelper.getAllyIconWhite(15));
    private final TLabel roundsLeftToExchange = new TLabel(Component.literal(String.valueOf(ClientMatch.getRoundsLeftToExchange())));
    private final TImage exchange = new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_begin_map_scene_exchange.png"));

    public RoundBeginMapSceneScreen() {
        super("RoundBeginMapSceneScreen");
        round.setHorizontalAlignment(HorizontalAlignment.CENTER);
        round.setFontSize(21);
        this.add(round);
        side.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(side);
        mapName.setBackground(0x60000000);
        mapName.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(mapName);
        this.add(background);
        ally.setFontSize(9);
        ally.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(ally);
        enemy.setFontSize(9);
        enemy.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(enemy);
        roundsLeftToExchange.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(roundsLeftToExchange);
        exchange.setImageFit(ImageFit.FIT);
        this.add(exchange);
        this.add(allyIcon);
        this.add(enemyIcon);
    }

    @Override
    public void layout() {
        round.setBounds(0, height / 2, width, round.getPreferredSize().y);
        LayoutHelper.BBottomOfA(side, 0, round);
        mapName.setBounds(width - mapName.getPreferredSize().x - 20, 33, mapName.getPreferredSize().x + 20, 21);
        background.setBounds((width - 222) / 2, height / 2 - 21 - 7, 222, 21);
        ally.setBounds(
                background.getX() + (73 - ally.getPreferredSize().x) / 2,
                background.getY() + (21 - ally.getPreferredSize().y) / 2,
                ally.getPreferredSize()
        );
        enemy.setBounds(
                width / 2 + 12 + 24 + (73 - enemy.getPreferredSize().x) / 2,
                ally.getY(),
                enemy.getPreferredSize()
        );
        roundsLeftToExchange.setBounds(0, background.getY() + (21 - roundsLeftToExchange.getPreferredSize().y) / 2, width, roundsLeftToExchange.getPreferredSize().y);
        exchange.setBounds(0, roundsLeftToExchange.getY() - 3, width, 16);
        allyIcon.setBounds(background.getX() + 79, background.getY() + 3, 15, 15);
        enemyIcon.setBounds(background.getX() + 127, background.getY() + 3, 15, 15);
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
    }
}
