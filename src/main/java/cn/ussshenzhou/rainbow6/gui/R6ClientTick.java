package cn.ussshenzhou.rainbow6.gui;

import cn.ussshenzhou.rainbow6.input.R6MouseHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class R6ClientTick {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        ClientMatch.tick();
        /*if ( InGameClientProperties.getIsWaitingMatchMaking()){
            InGameClientTimeCount.increaseMatchMakingTime();
        }*/
        if (InGameClientProperties.isUsingDrone){
            try {
                InGameClientProperties.getR6DroneGui().tick();
            } catch (Exception ignored) {
            }
        }
        if (event.phase== TickEvent.Phase.END){
            if (EE1DWarnGui.isRender){
                EE1DWarnGui.tick();
            }
        }
    }
    static double yaw=0;
    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event){
        R6MouseHelper.tick();
        /*try {
            if (yaw != InGameClientProperties.getR6DroneGui().getCurrentDrone().rotationYaw){
                yaw = InGameClientProperties.getR6DroneGui().getCurrentDrone().rotationYaw;
                LogManager.getLogger().warn(yaw);
            }
        } catch (Exception ignored){}*/
    }
}
