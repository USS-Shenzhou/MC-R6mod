package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class Test {

    public Test() {
    }

    @Decoder
    public Test(FriendlyByteBuf buf) {
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler(context.get().getSender());
        } else {
            clientHandler();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler() {
    }

    CompoundTag tag;

    public void serverHandler(ServerPlayer player) {
        //var a = PlayerDataTempStore.storeAndClear(player);
        tag = player.saveWithoutId(new CompoundTag());
        player.getInventory().clearContent();
        player.containerMenu.broadcastFullState();
        player.inventoryMenu.slotsChanged(player.getInventory());
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MinecraftServer minecraftServer = (MinecraftServer) LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
            minecraftServer.execute(() -> {
                player.load(tag);
                /*
                need manual restore:
                gamemode
                pos
                look

                 */
            });
        });
    }
}
