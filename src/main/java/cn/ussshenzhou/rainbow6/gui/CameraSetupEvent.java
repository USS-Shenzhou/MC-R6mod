package cn.ussshenzhou.rainbow6.gui;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class CameraSetupEvent {
    @SubscribeEvent
    public static void setSceneCamera(EntityViewRenderEvent.CameraSetup event){
        //event.setPitch(12.546349f);
        //event.setYaw(44.14949f);
    }
}
