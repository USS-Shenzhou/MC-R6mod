package cn.ussshenzhou.rainbow6.client.gui.hud;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author USS_Shenzhou
 */
public class PromptHud extends AutoCloseHud {
    private final TLabel header;
    private final TLabel content;
    protected float headerY0 = 0.3f;

    public PromptHud(Component headerText, Component contentText, int lifetime) {
        super(lifetime);
        header = new TLabel(headerText);
        content = new TLabel(contentText);
        header.setHorizontalAlignment(HorizontalAlignment.CENTER);
        header.setFontSize(14);
        this.add(header);
        content.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(content);
    }

    public PromptHud(Component headerText, Component contentText) {
        this(headerText, contentText, 100);
    }

    @Override
    public void layout() {
        header.setBounds(0, (int) (height * headerY0), width, header.getPreferredSize().y);
        LayoutHelper.BBottomOfA(content, 1, header);
        super.layout();
    }

    @Override
    public void resizeAsHud(int screenWidth, int screenHeight) {
        this.setAbsBounds(0, 0, screenWidth, screenHeight);
        super.resizeAsHud(screenWidth, screenHeight);
    }

    public static class ActStage extends PromptHud {

        public ActStage() {
            super(
                    new TranslatableComponent("gui.r6ms.round.act_stage"),
                    new TranslatableComponent(
                            ClientMatch.getSide() == Side.ATTACKER ? "gui.r6ms.round.act_stage.atk" : "gui.r6ms.round.act_stage.def"));
        }
    }

    public static class PrepareStage extends PromptHud {

        public PrepareStage() {
            super(
                    new TranslatableComponent("gui.r6ms.round.pre_stage"),
                    new TranslatableComponent(
                            ClientMatch.getSide() == Side.ATTACKER ? "gui.r6ms.round.pre_stage.atk" : "gui.r6ms.round.pre_stage.def")

            );
        }
    }

    public static class BombFound extends PromptHud {

        public BombFound() {
            super(
                    new TranslatableComponent(
                            ClientMatch.getSide() == Side.ATTACKER ? "gui.r6ms.round.found_bomb.atk" : "gui.r6ms.round.found_bomb.def"
                    ),
                    new TranslatableComponent(
                            ClientMatch.getSide() == Side.ATTACKER ? getBombSiteNamePrompt() : ""
                    )
            );
        }

        private static String getBombSiteNamePrompt() {
            Map.BombSite bombSite = ClientMatch.getMap().getBombSites().get(ClientMatch.getBombSiteNumber());
            return R6Constants.YELLOW + bombSite.getSubSite1Name() + "\n" + R6Constants.YELLOW + bombSite.getSubSite2Name();
        }
    }

    public static class AliveAmountHud extends PromptHud {
        public AliveAmountHud() {
            super(
                    new TextComponent(
                            ClientMatch.getSide() == Side.ATTACKER ?
                                    ClientMatch.getAttackerAliveAmount() + " VS " + ClientMatch.getDefenderAliveAmount()
                                    : ClientMatch.getDefenderAliveAmount() + " VS " + ClientMatch.getAttackerAliveAmount()
                    ),
                    new TextComponent("")
            );
            headerY0 = 0.2f;
        }
    }
}
