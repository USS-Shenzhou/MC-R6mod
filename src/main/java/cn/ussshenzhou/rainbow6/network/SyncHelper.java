package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.t88.network.NetworkHelper;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * @author USS_Shenzhou
 */
public class SyncHelper {

    /**
     * This method is copied and modified from com.alrex.parcool.common.network.SyncActionStateMessage under GPLv3.
     */
    public static void syncActionFromClient(Player player, SyncActionPacket.Encoder builder) {
        ByteBuffer buffer1 = builder.build();
        if (buffer1.limit() == 0) {
            return;
        }
        SyncActionPacket message = new SyncActionPacket();
        message.senderUUID = player.getUUID();
        message.buffer = new byte[buffer1.limit()];
        buffer1.get(message.buffer);
        NetworkHelper.sendToServer(message);
    }
}
