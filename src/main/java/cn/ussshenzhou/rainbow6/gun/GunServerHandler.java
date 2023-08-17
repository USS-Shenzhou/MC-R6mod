package cn.ussshenzhou.rainbow6.gun;

import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import cn.ussshenzhou.rainbow6.network.onlyto.server.GunShotFirePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

/**
 * @author USS_Shenzhou
 */
public class GunServerHandler {

    public static void shoot(NetworkEvent.Context context) {
        ServerPlayer sender = context.getSender();
        if (sender == null || sender.isSpectator()) {
            return;
        }
        var itemStack = sender.getItemInHand(InteractionHand.MAIN_HAND);
        if (!(itemStack.getItem() instanceof TestGun)){
            return;
        }
        //TODO
    }
}
