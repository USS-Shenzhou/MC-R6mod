package cn.ussshenzhou.rainbow6.client.gui.option;

import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.t88.config.ConfigHelper;

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
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.aim",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.aim = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).aim
        );
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.lean",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.lean = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).lean
        );
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.sprint",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.sprint = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).sprint
        );
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.crouch",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.crouch = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).crouch
        );
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.prone",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.prone = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).prone
        );
        addOptionCycleButton("gui.r6ms.main_menu.options.controls.walk",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.walk = button.getSelected().getContent()
                ),
                List.of(KeyTrig.values()),
                ConfigHelper.Universal.getConfigRead(Control.class).walk
        );
    }
}
