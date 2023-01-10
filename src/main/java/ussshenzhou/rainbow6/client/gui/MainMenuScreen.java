package ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.t88.gui.screen.TScreen;
import net.minecraft.network.chat.TextComponent;

/**
 * @author USS_Shenzhou
 */
public class MainMenuScreen extends TScreen {
    public MainMenuScreen() {
        super(new TextComponent("Main Menu"));
    }

    @Override
    public void onClose(boolean isFinal) {
        super.onClose(isFinal);
        ScreenManager.exitCurrentLayer();
    }
}
