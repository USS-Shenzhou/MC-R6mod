package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.hud.PlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.client.gui.hud.PromptHud;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TComponent;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

/**
 * @author USS_Shenzhou
 */
public class RoundEndScreen extends AbstractR6Screen {
    private final TLabel team = new TLabel(Component.translatable("gui.r6ms.round_end.your_team"));
    private final TLabel header;

    private RoundEndScreen(boolean win) {
        super("RoundEndScreen");
        team.setForeground(ClientMatch.getAllyTeamColor().getARGB());
        team.setFontSize(14);
        team.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(team);
        header = new TLabel(Component.translatable(win ? "gui.r6ms.round_end.win" : "gui.r6ms.round_end.lose", ClientMatch.getCurrentRoundNumber()));
        header.setFontSize(17.5f);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(header);
    }

    public static void roundEnd(boolean win) {
        RoundEndScreen roundEndScreen = new RoundEndScreen(win);
        HudManager.remove(HudManager.getChildren().stream().filter(tComponent
                -> tComponent instanceof PromptHud
                || tComponent instanceof PlayerInfoBarHud
        ).toArray(TComponent[]::new));
        ScreenManager.showNewLayerOverBg(roundEndScreen);
    }

    @Override
    public void layout() {
        team.setBounds(0, (int) (height * 0.25), width, team.getPreferredSize().y);
        LayoutHelper.BBottomOfA(header, 5, team, width, header.getPreferredSize().y);
        super.layout();
    }

    @Override
    protected void renderBackGround(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
    }
}
