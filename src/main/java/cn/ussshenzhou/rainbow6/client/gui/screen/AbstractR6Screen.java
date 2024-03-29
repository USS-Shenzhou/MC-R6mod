package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.t88.gui.screen.TScreen;
import cn.ussshenzhou.t88.gui.widegt.TWidget;
import net.minecraft.network.chat.Component;

/**
 * @author USS_Shenzhou
 */
public abstract class AbstractR6Screen extends TScreen {
    public AbstractR6Screen(String screenName) {
        super(Component.literal(screenName));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose(boolean isFinal) {
        if (isFinal) {
            this.tChildren.forEach(TWidget::onFinalClose);
        }
        ScreenManager.exitCurrentLayer();
    }
    //TODO forbidden close on esc
}
