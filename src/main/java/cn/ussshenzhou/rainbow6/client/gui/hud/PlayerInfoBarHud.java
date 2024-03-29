package cn.ussshenzhou.rainbow6.client.gui.hud;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.IconHelper;
import cn.ussshenzhou.rainbow6.util.Operator;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.TeamColor;
import cn.ussshenzhou.t88.gui.util.Border;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import cn.ussshenzhou.t88.gui.widegt.TTimer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class PlayerInfoBarHud extends TImage {
    private final TeamPlayers allies = new TeamPlayers();
    private final TeamPlayers enemies = new TeamPlayers();
    private final TImage allyIcon = new TImage(IconHelper.getAllyIconColored(20));
    private final TImage enemyIcon = new TImage(IconHelper.getEnemyIconColored(20));
    private final TLabel allyScore = new TLabel(Component.literal(String.valueOf(ClientMatch.getAllyScore())));
    private final TLabel enemyScore = new TLabel(Component.literal(String.valueOf(ClientMatch.getEnemyScore())));
    private final TLabel roundCount = new TLabel(Component.translatable("gui.r6ms.round_player_info_bar.round", ClientMatch.getCurrentRoundNumber()));
    private TTimer timer;

    public PlayerInfoBarHud(int countdownTime) {
        super(new ResourceLocation(R6Constants.MOD_ID, ClientMatch.getAllyTeamColor() == TeamColor.BLUE
                ? "textures/gui/round_player_info_blue.png"
                : "textures/gui/round_player_info_orange.png"
        ));
        this.add(allies);
        this.add(enemies);
        this.add(allyIcon);
        this.add(enemyIcon);
        allyScore.setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setAutoScroll(false)
                .setForeground(ClientMatch.getAllyTeamColor().getARGB());
        this.add(allyScore);
        enemyScore.setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setAutoScroll(false)
                .setForeground(ClientMatch.getAllyTeamColor().opposite().getARGB());
        this.add(enemyScore);
        roundCount.setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setAutoScroll(false)
                .setFontSize(R6Constants.FONT_TINY_2);
        this.add(roundCount);
        timer = TTimer.newTimerCountDown(countdownTime);
        timer.setHorizontalAlignment(HorizontalAlignment.CENTER);
        timer.setShowFullFormat(true);
        timer.setKeepDigitsLength(false);
        timer.setShowUpto(TTimer.TimeCategory.MIN);
        this.add(timer);
    }

    @Override
    public void tickT() {
        if (timer.getTime() <= 10 * 1000) {
            timer.setBackground(0xffff0000);
        } else {
            timer.setBackground(0x00000000);
        }
        super.tickT();
    }

    @Override
    public void layout() {
        allies.setBounds(10, 2, 57, 12);
        enemies.setBounds(width - 10 - 57, 2, 57, 12);
        allyIcon.setBounds(71, 4, 10, 10);
        enemyIcon.setBounds(width - 71 - 10, 4, 10, 10);
        //+1 to compensate visual mistake
        LayoutHelper.BRightOfA(allyScore, 1, allyIcon);
        LayoutHelper.BLeftOfA(enemyScore, 0, enemyIcon);
        roundCount.setBounds(0, height, width, roundCount.getPreferredSize().y);
        timer.setBounds(width / 2 - 13, 0, 26, 18);
        super.layout();
    }

    @Override
    public void resizeAsHud(int screenWidth, int screenHeight) {
        this.setAbsBounds((screenWidth - 216) / 2, 0, 216, 18);
        super.resizeAsHud(screenWidth, screenHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        //super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderTop(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        super.renderTop(graphics, pMouseX, pMouseY, pPartialTick);
    }

    public TeamPlayers getAllies() {
        return allies;
    }

    public TeamPlayers getEnemies() {
        return enemies;
    }

    public TTimer getTimer() {
        return timer;
    }

    public void resetTimer(int countdownTime) {
        this.timer = TTimer.newTimerCountDown(countdownTime);
        this.layout();
    }

    public static class TeamPlayers extends TPanel {
        //TODO HP
        private ArrayList<TImage> list = new ArrayList<>(5);

        public TeamPlayers() {
            super();
            for (int i = 0; i < 5; i++) {
                list.add(new TImage(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/player_info_bar_unknown.png")));
            }
            list.forEach(image -> {
                image.setImageFit(ImageFit.STRETCH);
                this.add(image);
            });
        }

        @Override
        public void layout() {
            list.get(0).setBounds(0, 0, 9, 12);
            for (int i = 1; i < 5; i++) {
                LayoutHelper.BRightOfA(list.get(i), 3, list.get(i - 1));
            }
            super.layout();
        }

        public void setPlayerPrepared(int index) {
            list.get(index).setImageLocation(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/player_info_bar_prepared.png"));
        }

        public void setPlayerOperator(int index, Operator operator) {
            //list.get(index).setImageLocation(operator);
            //TODO
        }

        public void setPlayerDead(int index) {
            //TODO
        }
    }
}
