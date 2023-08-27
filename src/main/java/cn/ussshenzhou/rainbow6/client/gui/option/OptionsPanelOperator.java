package cn.ussshenzhou.rainbow6.client.gui.option;

import cn.ussshenzhou.rainbow6.gun.hit.ClientEntityModelHelper;
import cn.ussshenzhou.rainbow6.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * @author USS_Shenzhou
 */
public class OptionsPanelOperator extends OptionsPanel {
    public OptionsPanelOperator() {
        super();
    }

    @Override
    protected void initOptions() {
        var unit = addOptionButton(Component.empty(), Component.translatable("gui.r6ms.main_menu.options.operator.upload_model"),
                pButton -> {
                    if (ClientUtils.isOp()) {
                        ClientEntityModelHelper.sendModelsToServer();
                    }
                    //TODO else pop-out warn
                });
        unit.setAutoBackground(false);
        unit.setBackground(0);
    }
}
