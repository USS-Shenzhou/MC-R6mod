package cn.ussshenzhou.rainbow6.effects;

import cn.ussshenzhou.rainbow6.mixin.MixinEntityAccessor;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Ee1dEvent {
    @SubscribeEvent
    public static void beforeLivingRenderer(RenderLivingEvent.Pre event) {
        MixinEntityAccessor livingEntity = (MixinEntityAccessor) event.getEntity();
        if (event.getBuffers() instanceof OutlineLayerBuffer) {
            if (livingEntity.r6GetFlag(8 + 0)) {
                ((OutlineLayerBuffer) event.getBuffers()).setColor(0xde, 0x33, 0x2f, 0xbb);
            }
        }
    }

    @SubscribeEvent
    public static void effectEndEvent(PotionEvent.PotionExpiryEvent event) {
        if (event.getPotionEffect().getPotion() == ModEffects.EXPOSED) {
            MixinEntityAccessor mixinEntityAccessor = (MixinEntityAccessor) event.getEntityLiving();
            mixinEntityAccessor.r6SetFlag(6, false);
            event.getEntityLiving().setGlowing(false);
            mixinEntityAccessor.r6SetFlag(8 + 0, false);
        }
    }
}
