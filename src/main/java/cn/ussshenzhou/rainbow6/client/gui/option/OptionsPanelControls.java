package cn.ussshenzhou.rainbow6.client.gui.option;

import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.t88.config.ConfigHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * @author USS_Shenzhou
 */
public class OptionsPanelControls extends OptionsPanel {

    public OptionsPanelControls() {
        super();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void initOptions() {
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.aim"),
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.aim = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).aim
        );
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.lean"),
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.lean = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).lean
        );
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.sprint"),
                button -> {
                    var c = button.getSelected().getContent();
                    ConfigHelper.Universal.getConfigRead(Control.class).sprint = c;
                    Minecraft.getInstance().options.toggleSprint().set(c == KeyTrig.TOGGLE);
                },
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).sprint
        );
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.crouch"),
                button -> {
                    var c = button.getSelected().getContent();
                    ConfigHelper.Universal.getConfigRead(Control.class).crouch = c;
                    Minecraft.getInstance().options.toggleCrouch().set(c == KeyTrig.TOGGLE);
                },
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).crouch
        );
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.prone"),
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.prone = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).prone
        );
        addOptionCycleButton(Component.translatable("gui.r6ms.main_menu.options.controls.walk"),
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.walk = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).walk
        );
    }
}
