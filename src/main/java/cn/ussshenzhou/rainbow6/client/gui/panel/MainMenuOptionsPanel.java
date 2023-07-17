package cn.ussshenzhou.rainbow6.client.gui.panel;

import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageCycleButton;
import cn.ussshenzhou.rainbow6.client.gui.widget.OptionsPanel;
import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.config.ConfigHelper;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TCycleButton;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class MainMenuOptionsPanel extends TPanel {
    private final TImage background = new TImage(R6Constants.STD_BACKGROUND);

    private final LinkedHashMap<FocusSensitiveImageSelectButton, OptionsPanel> categories = new LinkedHashMap<>();

    private final FocusSensitiveImageSelectButton controls = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.options.controls"),
            pButton -> selectCategory(this.controls),
            //needtest align?
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_cutout_unselected12.png"),
            new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_glow_selected.png")
    );
    private final OptionsPanel controlsPanel = new OptionsPanel();

    public MainMenuOptionsPanel() {
        this.add(background);

        initPair(controls, controlsPanel);
        initControls();
        selectCategory(controls);
    }

    private void initPair(FocusSensitiveImageSelectButton button, OptionsPanel panel) {
        this.add(button);
        this.add(panel);
        categories.put(button, panel);
        button.getText().setFontSize(R6Constants.FONT_SMALL_3);
        button.setPadding(R6Constants.PADDING_SMALL);
        button.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);
        button.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        button.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        panel.setVisibleT(false);
    }

    private void initControls() {
        initControlsInternal("gui.r6ms.main_menu.options.controls.aim",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.aim = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).aim
        );
        initControlsInternal("gui.r6ms.main_menu.options.controls.lean",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.lean = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).lean
        );
        initControlsInternal("gui.r6ms.main_menu.options.controls.sprint",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.sprint = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).sprint
        );
        initControlsInternal("gui.r6ms.main_menu.options.controls.crouch",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.crouch = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).crouch
        );
        initControlsInternal("gui.r6ms.main_menu.options.controls.prone",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.prone = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).prone
        );
        initControlsInternal("gui.r6ms.main_menu.options.controls.walk",
                button -> ConfigHelper.Universal.getConfigWrite(
                        Control.class, control -> control.walk = button.getSelected().getContent()
                ),
                ConfigHelper.Universal.getConfigRead(Control.class).walk
        );
    }

    private void initControlsInternal(String title, Consumer<TCycleButton<KeyTrig>> buttonConsumer,KeyTrig defaultValue) {
        HoverSensitiveImageCycleButton<KeyTrig> button = new HoverSensitiveImageCycleButton<>(
                new ResourceLocation(R6Constants.MOD_ID,"textures/gui/button_std_white.png"),
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_hovered.png")
        );
        Arrays.stream(KeyTrig.values()).forEach(k -> button.getButton().addElement(k, buttonConsumer));
        button.getButton().select(defaultValue);
        button.setPadding(R6Constants.PADDING_TINY);
        button.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        button.getText().setFontSize(R6Constants.FONT_SMALL_3);
        button.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        button.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);
        OptionsPanel.ConfigUnit unit = new OptionsPanel.ConfigUnit(title, button);
        unit.getTitle().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        unit.getTitle().setFontSize(R6Constants.FONT_SMALL_3);
        controlsPanel.add(unit);
    }

    private void selectCategory(FocusSensitiveImageSelectButton button) {
        categories.keySet().forEach(i -> i.setSelected(false));
        categories.values().forEach(p -> p.setVisibleT(false));
        button.setSelected(true);
        categories.get(button).setVisibleT(true);
    }

    int i = 0;

    @Override
    public void layout() {
        background.setBounds(0, 0, width, height);
        int x0 = (int) (width * 0.2);
        int y0 = (int) (height * 0.2);
        int w = (int) (width * 0.6);
        int h = (int) (height * 0.6);
        i = 0;
        categories.forEach(((button, panel) -> {
            if (i == 0) {
                button.setBounds(22, 14, 50, 14);
            } else {
                LayoutHelper.BRightOfA(button, -4, categories.keySet().stream().toList().get(i - 1));
            }
            panel.setBounds(x0, y0, w, h);
            i++;
        }));
        super.layout();
    }
}
