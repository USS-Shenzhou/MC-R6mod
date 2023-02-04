package cn.ussshenzhou.rainbow6.client.gui.widgets;

import cn.ussshenzhou.rainbow6.util.Operators;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class MatchPlayerInfoBarHud extends TImage {
    private final TeamPlayers allies = new TeamPlayers();
    private final TeamPlayers enemies = new TeamPlayers();

    public MatchPlayerInfoBarHud() {
        super(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/round_player_info_blue.png"));
        this.add(allies);
        this.add(enemies);
    }

    @Override
    public void layout() {
        allies.setBounds(10, 2, 57, 12);
        enemies.setBounds(width - 10 - 57, 2, 57, 12);
        super.layout();
    }

    @Override
    public void resizeAsHud(int screenWidth, int screenHeight) {
        this.setBounds((screenWidth - 216) / 2, 0, 216, 18);
        super.resizeAsHud(screenWidth, screenHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        //super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderTop(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.renderTop(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public TeamPlayers getAllies() {
        return allies;
    }

    public TeamPlayers getEnemies() {
        return enemies;
    }

    public class TeamPlayers extends TPanel {
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

        public void setPlayerPrepared(int index){
            list.get(index).setImageLocation(new ResourceLocation(R6Constants.MOD_ID, "textures/gui/player_info_bar_prepared.png"));
        }

        public void setPlayerOperator(int index, Operators operator){
            //list.get(index).setImageLocation(operator);
        }
    }
}
