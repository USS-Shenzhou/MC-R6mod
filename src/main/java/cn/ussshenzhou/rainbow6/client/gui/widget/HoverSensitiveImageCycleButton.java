package cn.ussshenzhou.rainbow6.client.gui.widget;

import cn.ussshenzhou.t88.gui.advanced.THoverSensitiveImageButton;
import cn.ussshenzhou.t88.gui.event.TWidgetContentUpdatedEvent;
import cn.ussshenzhou.t88.gui.widegt.TCycleButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.SubscribeEvent;

/**
 * @author USS_Shenzhou
 */
public class HoverSensitiveImageCycleButton<E> extends THoverSensitiveImageButton {

    public HoverSensitiveImageCycleButton(ResourceLocation backgroundImageLocation, ResourceLocation backgroundImageLocationHovered) {
        super(Component.literal(""), pButton -> {
        }, backgroundImageLocation, backgroundImageLocationHovered);
        this.remove(this.button);
        this.button = new TCycleButton<E>() {
            @Override
            public void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
                //this.renderString(poseStack, Minecraft.getInstance().font, getForeground());
                return;
            }
        };
        this.add(this.button);
        NeoForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TCycleButton<E> getButton() {
        return (TCycleButton<E>) super.getButton();
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onCycleButtonChange(TWidgetContentUpdatedEvent event) {
        if (event.getUpdated() == this.button) {
            try {
                this.text.setText(((TCycleButton<E>) button).getSelected().getNarration());
            } catch (NullPointerException ignored) {
                this.text.setText(Component.empty());
            }
        }
    }

    @Override
    public void onFinalClose() {
        NeoForge.EVENT_BUS.unregister(this);
        super.onFinalClose();
    }
}
