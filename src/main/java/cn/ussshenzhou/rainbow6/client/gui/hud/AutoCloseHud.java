package cn.ussshenzhou.rainbow6.client.gui.hud;

import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.widegt.TComponent;
import cn.ussshenzhou.t88.gui.widegt.TPanel;

/**
 * @author USS_Shenzhou
 */
public abstract class AutoCloseHud extends TPanel {
    private int lifeTime;

    public AutoCloseHud(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public AutoCloseHud() {
        this(100);
    }

    public static void ifPresentFormerThenRemoveAndAdd(AutoCloseHud newAutoCloseHud) {
        HudManager.remove(HudManager.getChildren().stream().filter(tComponent -> tComponent instanceof AutoCloseHud).toArray(TComponent[]::new));
        HudManager.add(newAutoCloseHud);
    }

    @Override
    public void tickT() {
        super.tickT();
        lifeTime--;
        if (lifeTime <= 0) {
            HudManager.remove(this);
        }
    }
}
