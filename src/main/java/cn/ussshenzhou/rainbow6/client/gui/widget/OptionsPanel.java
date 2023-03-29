package cn.ussshenzhou.rainbow6.client.gui.widget;

import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;

/**
 * @author USS_Shenzhou
 */
public class OptionsPanel extends TScrollPanel {
    private int gap = 1;
    private int unitHeight = 16;

    public OptionsPanel() {
        super();
        this.scrollbarGap = 4;
        this.setBackground(0);
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setUnitHeight(int unitHeight) {
        this.unitHeight = unitHeight;
    }

    @Override
    public void layout() {
        for (int i = 0; i < children.size(); i++) {
            TWidget t = children.get(i);
            if (t instanceof ConfigUnit unit) {
                if (i == 0) {
                    children.get(0).setBounds(0, 0, getUsableWidth(), unitHeight);
                } else {
                    LayoutHelper.BBottomOfA(children.get(i), gap, children.get(i - 1));
                }
                if (i % 2 == 0) {
                    unit.title.setBackground(0x1affffff);
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
            int l1 = (int)((float)(this.height * this.height) / (float)this.bottomY);
            l1 = Mth.clamp(l1, 32, this.getY() + this.height - this.getY() - 8);
            int i2 = (int)this.getScrollAmount() * (this.getY() + this.height - this.getY() - l1) / k1 + this.getY();
            if (i2 < this.getY()) {
                i2 = this.getY();
            }

            RenderSystem.enableBlend();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex((double)i, (double)(this.getY() + this.height), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double)j, (double)(this.getY() + this.height), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double)j, (double)this.getY(), 0.0).color(255, 255, 255, 0x33).endVertex();
            bufferbuilder.vertex((double)i, (double)this.getY(), 0.0).color(255, 255, 255, 0x33).endVertex();

            bufferbuilder.vertex((double)i, (double)(i2 + l1), 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double)j, (double)(i2 + l1), 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double)j, (double)i2, 0.0).color(255, 255, 255, 0xdd).endVertex();
            bufferbuilder.vertex((double)i, (double)i2, 0.0).color(255, 255, 255, 0xdd).endVertex();
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
        private TLabel title;
        private TComponent setter;

        public ConfigUnit(TLabel title, TComponent setter) {
            this.title = title;
            this.setter = setter;
            this.add(title);
            this.add(setter);
        }

        public ConfigUnit(String translatableKeyTitle, TComponent setter) {
            title = new TLabel(Component.translatable(translatableKeyTitle));
            this.setter = setter;
            this.add(title);
            this.add(setter);
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
    }
}
