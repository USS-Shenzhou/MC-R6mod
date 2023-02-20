package cn.ussshenzhou.rainbow6.client.gui.hud;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author USS_Shenzhou
 */
public class ActStagePromptHud extends AutoCloseHud{
    private final TLabel actStage = new TLabel(new TranslatableComponent("gui.r6ms.round.act_stage"));
    private final TLabel prompt = new TLabel(new TranslatableComponent(
            ClientMatch.getSide() == Side.ATTACKER ? "gui.r6ms.round.act_stage.atk" : "gui.r6ms.round.act_stage.def"));

    public ActStagePromptHud() {
        super();
        actStage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        actStage.setFontSize(14);
        this.add(actStage);
        prompt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(prompt);
    }

    @Override
    public void layout() {
        actStage.setBounds(0, (int) (height * 0.3), width, actStage.getPreferredSize().y);
        LayoutHelper.BBottomOfA(prompt, 1, actStage);
        super.layout();
    }

    @Override
    public void resizeAsHud(int screenWidth, int screenHeight) {
        this.setAbsBounds(0, 0, screenWidth, screenHeight);
        super.resizeAsHud(screenWidth, screenHeight);
    }
}
