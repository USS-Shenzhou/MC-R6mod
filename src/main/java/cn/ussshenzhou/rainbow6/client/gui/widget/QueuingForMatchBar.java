package cn.ussshenzhou.rainbow6.client.gui.widget;

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
    private int showGetQueued = -1;
    private TLabel getQueued = new TLabel(new TranslatableComponent("gui.r6ms.main_menu.header.queued"));

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
        timer.setFontSize(R6Constants.FONT_SMALL_3);
        this.add(timer);
        this.add(getQueued);
        getQueued.setVisibleT(false);
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

    @Override
    public void tickT() {
        if (showGetQueued >= 0) {
            showGetQueued--;
        }
        super.tickT();
    }

    public void start() {
        this.setVisibleT(true);
        timer.start();
    }

    public void stop() {
        if (showGetQueued < 0) {
            this.setVisibleT(false);
        }
        timer.stop();
    }

    public void showGetQueued() {
        showGetQueued = 20;
        timer.setVisibleT(false);
        gameMode.setVisibleT(false);
        getQueued.setVisibleT(true);
        this.setBackground(R6Constants.CYAN_STD_ARGB);
    }
}
