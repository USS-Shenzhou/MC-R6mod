package cn.ussshenzhou.rainbow6.client.gui.option;

import cn.ussshenzhou.rainbow6.client.gui.widget.FocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TImage;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

/**
 * @author USS_Shenzhou
 */
@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class MainMenuOptionsPanel extends TPanel {
    private static final ResourceLocation BUTTON_UNSELECTED = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_cutout_unselected12.png");
    private static final ResourceLocation BUTTON_SELECTED = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_glow_selected.png");


    private final TImage background = new TImage(R6Constants.STD_BACKGROUND);

    private final LinkedHashMap<FocusSensitiveImageSelectButton, OptionsPanel> categories = new LinkedHashMap<>();

    private final FocusSensitiveImageSelectButton controls = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.options.controls"),
            pButton -> selectCategory(this.controls),
            //needtest align?
            BUTTON_UNSELECTED, BUTTON_SELECTED);
    private final FocusSensitiveImageSelectButton operator = new FocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.main_menu.options.operator"),
            pButton -> selectCategory(this.operator), BUTTON_UNSELECTED, BUTTON_SELECTED
    );
    private final OptionsPanelControls controlsPanel = new OptionsPanelControls();
    private final OptionsPanelOperator operatorPanel = new OptionsPanelOperator();

    public MainMenuOptionsPanel() {
        this.add(background);
        initPair(controls, controlsPanel);

        initPair(operator,operatorPanel);
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
        int x0 = (int) (width * 0.15);
        int y0 = (int) (height * 0.15);
        int w = (int) (width * 0.7);
        int h = (int) (height * 0.7);
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
