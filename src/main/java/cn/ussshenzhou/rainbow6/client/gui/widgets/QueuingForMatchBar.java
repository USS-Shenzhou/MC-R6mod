package cn.ussshenzhou.rainbow6.client.gui.widgets;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import cn.ussshenzhou.t88.gui.widegt.TTimer;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * @author USS_Shenzhou
 */
public class QueuingForMatchBar extends TPanel {
    private TLabel gameMode = new TLabel(new TranslatableComponent("gui.r6ms.main_menu.header.quick_match"));
    private TTimer timer = new TTimer();

    public QueuingForMatchBar() {
        super();
        gameMode.setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(gameMode);
        this.setBackground(0xff32b2ff);
        timer.setShowUpto(TTimer.TimeCategory.MIN);
        timer.setShowFullFormat(true);
        timer.setBackground(0xff000000);
        timer.setForeground(0xfffff43a);
        timer.setPrefix(" ⌚ ");
        timer.setFontSize(R6Constants.FONT_SMALL);
        this.add(timer);
    }

    @Override
    public void layout() {
        gameMode.setBounds((this.width - gameMode.getPreferredSize().x) / 2, 0, gameMode.getPreferredSize().x, this.height);
        timer.setBounds(gameMode.getX() + gameMode.getSize().x + 5, (this.height - 9) / 2 + 1, 34, 9);
        super.layout();
    }

    @Override
    public void resizeAsHud(int screenWidth, int screenHeight) {
        this.setBounds(0, 0, screenWidth, 12);
        super.resizeAsHud(screenWidth, screenHeight);
    }

    public void start() {
        this.setVisibleT(true);
        timer.start();
    }

    public void stop() {
        this.setVisibleT(false);
        timer.stop();
    }
}
