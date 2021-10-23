package cn.ussshenzhou.rainbow6.matchmaking;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.DEDICATED_SERVER)
public class R6ServerTick {
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){
        MatchMaking.checkPlayerList();
        for (ServerMatch match : InGameServerProperties.getServerMatches()){
            match.tick();
        }
    }
}
