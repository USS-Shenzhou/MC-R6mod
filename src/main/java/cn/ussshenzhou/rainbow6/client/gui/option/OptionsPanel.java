package cn.ussshenzhou.rainbow6.client.gui.option;

import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageButton;
import cn.ussshenzhou.rainbow6.client.gui.widget.HoverSensitiveImageCycleButton;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.ImageFit;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author USS_Shenzhou
 */
public abstract class OptionsPanel extends TScrollPanel {
    private int gap = 1;
    private int unitHeight = 16;
    private static final int DEFAULT_TITLE_BACKGROUND = 0x1affffff;

    public OptionsPanel() {
        super();
        this.scrollbarGap = 4;
        this.setBackground(0);
        this.initOptions();
    }

    protected abstract void initOptions();

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setUnitHeight(int unitHeight) {
        this.unitHeight = unitHeight;
    }

    public <T> ConfigUnit addOptionCycleButton(Component title, Consumer<TCycleButton<T>> pressAction, List<T> values, T defaultValue) {
        HoverSensitiveImageCycleButton<T> button = new HoverSensitiveImageCycleButton<>(
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_white.png"),
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_hovered.png")
        );
        values.forEach(k -> button.getButton().addElement(k, pressAction));
        button.getButton().select(defaultValue);
        OptionsPanel.ConfigUnit unit = new ConfigUnit(title, button);
        initOptionButton(button, unit);
        return unit;
    }

    public ConfigUnit addOptionButton(Component title, Component buttonText, Button.OnPress pressAction) {
        HoverSensitiveImageButton button = new HoverSensitiveImageButton(buttonText, pressAction,
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button_std_white.png"),
                new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button16_hovered.png")
        );
        OptionsPanel.ConfigUnit unit = new ConfigUnit(title, button);
        initOptionButton(button, unit);
        return unit;
    }

    protected void initOptionButton(HoverSensitiveImageButton button, ConfigUnit unit) {
        button.setPadding(R6Constants.PADDING_TINY);
        button.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        button.getText().setFontSize(R6Constants.FONT_SMALL_3);
        button.getBackgroundImage().setImageFit(ImageFit.STRETCH);
        button.getBackgroundImageHovered().setImageFit(ImageFit.STRETCH);
        unit.getTitle().setHorizontalAlignment(HorizontalAlignment.RIGHT);
        unit.getTitle().setFontSize(R6Constants.FONT_SMALL_3);
        this.add(unit);
    }

    @Override
    public void layout() {
        for (int i = 0; i < children.size(); i++) {
            TWidget t = children.get(i);
            if (t instanceof ConfigUnit unit) {
                if (i == 0) {
                    children.get(0).setBounds(0, 0, getUsableWidth(), unitHeight);
                    if (unit.autoBackground) {
                        unit.title.setBackground(DEFAULT_TITLE_BACKGROUND);
                    }
                } else {
                    LayoutHelper.BBottomOfA(children.get(i), gap, children.get(i - 1));
                    if (unit.autoBackground) {
                        if (((ConfigUnit) children.get(i - 1)).title.getBackground() != DEFAULT_TITLE_BACKGROUND) {
                            unit.title.setBackground(DEFAULT_TITLE_BACKGROUND);
                        } else {
                            unit.title.setBackground(0x00000000);
                        }
                    }
                }
            }
        }
        super.layout();
    }

    @Override
    protected void renderScrollBar() {
        int k1 = this.getMaxScroll();
        if (k1 > 0) {
            int i = this.getScrollBarX();
            int j = i + 3;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            int l1 = (int) ((float) (this.height * this.height) / (float) this.bottomY);
            l1 = Mth.clamp(l1, 32, this.getYT() + this.height - this.getYT() - 8);
            int i2 = (int) this.getScrollAmount() * (this.getYT() + this.height - this.getYT() - l1) / k1 + this.getYT();
            if (i2 < this.getYT()) {
                i2 = this.getYT();
            }

            RenderSystem.enableBlend();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex((double) i, (double) (this.getYT() + this.height), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double) j, (double) (this.getYT() + this.height), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double) j, (double) this.getYT(), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double) i, (double) this.getYT(), 0.0).color(255, 255, 255, 0x33).endVertex();

            bufferbuilder.vertex((double) i, (double) (i2 + l1), 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double) j, (double) (i2 + l1), 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double) j, (double) i2, 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double) i, (double) i2, 0.0).color(255, 255, 255, 0xdd).endVertex();
            tesselator.end();
        }

    }

    @Override
    public int getUsableWidth() {
        return this.width - 3 - this.scrollbarGap;
    }

    public static <E> ConfigUnit fromEnum(String translatableKeyTitle, List<TCycleButton<E>.Entry> entries, ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationHovered) {
        var button = new HoverSensitiveImageCycleButton<E>(backgroundImageLocation, backgroundImageLocationHovered);
        entries.forEach(entry -> button.getButton().addElement(entry));
        return new ConfigUnit(translatableKeyTitle, button);
    }

    public static class ConfigUnit extends TPanel {
        protected TLabel title;
        protected TComponent setter;
        private boolean autoBackground = true;

        public ConfigUnit(TLabel title, TComponent setter) {
            this.title = title;
            this.setter = setter;
            this.add(title);
            this.add(setter);
        }

        public ConfigUnit(String translatableKeyTitle, TComponent setter) {
            this(new TLabel(Component.translatable(translatableKeyTitle)), setter);
        }

        public ConfigUnit(Component title, TComponent setter) {
            this(new TLabel(title), setter);
        }

        @Override
        public void layout() {
            title.setBounds(0, 1, width / 2 + 1, height - 2);
            if (setter instanceof HoverSensitiveImageButton) {
                setter.setBounds(width / 2, 0, width / 2, height);
            } else {
                setter.setBounds(width / 2, 1, width / 2, height - 2);
            }
            super.layout();
        }

        public TLabel getTitle() {
            return title;
        }

        public TComponent getSetter() {
            return setter;
        }

        public boolean autoBackground() {
            return autoBackground;
        }

        public void setAutoBackground(boolean autoBackground) {
            this.autoBackground = autoBackground;
        }
    }
}
