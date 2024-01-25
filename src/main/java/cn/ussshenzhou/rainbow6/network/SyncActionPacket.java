package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.action.Action;
import cn.ussshenzhou.rainbow6.action.Actions;
import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.t88.network.NetworkHelper;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.NetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * This file is copied and modified from com.alrex.parcool.common.network.SyncActionStateMessage under GPLv3.
 *
 * @author USS_Shenzhou
 */
@NetPacket
public class SyncActionPacket {
    protected SyncActionPacket() {
    }

    protected UUID senderUUID = null;
    protected byte[] buffer = null;

    @cn.ussshenzhou.t88.network.annotation.Encoder
    public void encode(FriendlyByteBuf packetBuffer) {
        packetBuffer
                .writeLong(senderUUID.getMostSignificantBits())
                .writeLong(senderUUID.getLeastSignificantBits())
                .writeInt(buffer.length)
                .writeBytes(buffer);
    }

    @cn.ussshenzhou.t88.network.annotation.Decoder
    public static SyncActionPacket decode(FriendlyByteBuf packetBuffer) {
        SyncActionPacket message = new SyncActionPacket();
        message.senderUUID = new UUID(packetBuffer.readLong(), packetBuffer.readLong());
        int size = packetBuffer.readInt();
        message.buffer = new byte[size];
        packetBuffer.readBytes(message.buffer);
        return message;
    }

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            serverHandler(context);
        } else {
            clientHandler(context);
        }
    }

    public void serverHandler(Supplier<NetworkEvent.Context> contextSupplier) {
        Player player = contextSupplier.get().getSender();
        if (player == null) {
            return;
        }
        /*player.getLevel().getNearbyPlayers(TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(16 * 12))
                .forEach(p -> NetworkHelper.getChannel(SyncActionPacket.class).send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) p), this));*/
        NetworkHelper.getChannel(SyncActionPacket.class).send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), this);
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }

        SyncActionPacket.Decoder decoder = new SyncActionPacket.Decoder(this.buffer, actionCapability);
        while (decoder.hasNext()) {
            SyncActionPacket.ActionSyncData item = decoder.getItem();
            if (item == null) {
                continue;
            }
            Action action = item.getAction();
            switch (item.getType()) {
                case START -> {
                    action.setDoing(true);
                    action.onStartInServer(player, actionCapability, item.getBuffer());
                    action.onStart(player, actionCapability);
                }
                case FINISH -> {
                    action.setDoing(false);
                    action.onStopInServer(player);
                    action.onStop(player);
                }
                case NORMAL -> action.restoreSynchronizedState(item.getBuffer());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler(Supplier<NetworkEvent.Context> contextSupplier) {
        Player player;
        //boolean clientSide;
        Level world = Minecraft.getInstance().level;
        if (world == null) {
            return;
        }
        player = world.getPlayerByUUID(senderUUID);
        if (player == null || player.isLocalPlayer()) {
            return;
        }
        ActionCapability actionCapability = ActionCapability.get(player);
        if (actionCapability == null) {
            return;
        }

        SyncActionPacket.Decoder decoder = new SyncActionPacket.Decoder(this.buffer, actionCapability);
        while (decoder.hasNext()) {
            SyncActionPacket.ActionSyncData item = decoder.getItem();
            if (item == null) {
                continue;
            }
            Action action = item.getAction();
            switch (item.getType()) {
                case START -> {
                    action.setDoing(true);
                    action.onStartInOtherClient(player, actionCapability, item.getBuffer());
                    action.onStart(player, actionCapability);
                }
                case FINISH -> {
                    action.setDoing(false);
                    action.onStopInOtherClient(player);
                    action.onStop(player);
                }
                case NORMAL -> action.restoreSynchronizedState(item.getBuffer());
            }
        }
    }

    public static class Encoder {
        private static final Encoder instance = new Encoder();

        private Encoder() {
        }

        private final ByteBuffer buffer = ByteBuffer.allocate(1024);

        public static Encoder reset() {
            instance.buffer.clear();
            return instance;
        }

        public Encoder appendSyncData(ActionCapability actionCapability, Action action, ByteBuffer actionBuffer) {
            return append(SyncActionPacket.DataType.NORMAL, actionCapability, action, actionBuffer);
        }

        public Encoder appendStartData(ActionCapability actionCapability, Action action, ByteBuffer actionBuffer) {
            return append(SyncActionPacket.DataType.START, actionCapability, action, actionBuffer);
        }

        public Encoder appendFinishMsg(ActionCapability actionCapability, Action action) {
            short id = Actions.indexOf(action);
            if (id < 0) {
                return this;
            }
            buffer.putShort(id)
                    .put(SyncActionPacket.DataType.FINISH.getCode())
                    .putInt(0);
            return this;
        }

        private Encoder append(SyncActionPacket.DataType type, ActionCapability actionCapability, Action action, ByteBuffer actionBuffer) {
            short id = Actions.indexOf(action);
            if (id < 0) {
                return this;
            }
            buffer.putShort(id)
                    .put(type.getCode())
                    .putInt(actionBuffer.limit())
                    .put(actionBuffer);
            return this;
        }

        public ByteBuffer build() {
            buffer.flip();
            return buffer;
        }
    }

    private static class Decoder {
        ByteBuffer buffer;
        ActionCapability actionCapability;

        Decoder(byte[] buf, ActionCapability actionCapability) {
            buffer = ByteBuffer.wrap(buf);
            this.actionCapability = actionCapability;
        }

        public boolean hasNext() {
            return buffer.position() < buffer.limit();
        }

        @Nullable
        public SyncActionPacket.ActionSyncData getItem() {
            Action action = actionCapability.getInstanceOf(Actions.values()[buffer.getShort()]);
            SyncActionPacket.DataType type = SyncActionPacket.DataType.getFromCode(buffer.get());
            int bufferSize = buffer.getInt();
            if (bufferSize > 1024) {
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("Synchronization failed. demanded buffer size is too large\n")
                        .append(action)
                        .append(":Sync_Type")
                        .append(type)
                        .append('\n')
                        .append(buffer);
                if (buffer.limit() < 128) {
                    buffer.rewind();
                    msgBuilder.append("->{");
                    while (buffer.hasRemaining()) {
                        msgBuilder.append(Integer.toHexString(buffer.get()))
                                .append(',');
                    }
                    msgBuilder.append('}');
                }
                LogUtils.getLogger().warn(msgBuilder.toString());
                buffer.position(buffer.limit());
                return null;
            }
            byte[] array = new byte[bufferSize];
            buffer.get(array);
            if (action == null) {
                return null;
            }
            ByteBuffer buf = ByteBuffer.wrap(array);
            return new SyncActionPacket.ActionSyncData(action, buf, type);
        }
    }

    private enum DataType {
        NORMAL, START, FINISH;

        public byte getCode() {
            return switch (this) {
                case START -> 1;
                case FINISH -> 2;
                default -> 0;
            };
        }

        public static DataType getFromCode(byte code) {
            return switch (code) {
                case 1 -> START;
                case 2 -> FINISH;
                default -> NORMAL;
            };
        }
    }

    private static class ActionSyncData {
        Action action;
        ByteBuffer buffer;
        DataType type;

        public ActionSyncData(Action action, ByteBuffer buffer, DataType type) {
            this.action = action;
            this.buffer = buffer;
            this.type = type;
        }

        public DataType getType() {
            return type;
        }

        public Action getAction() {
            return action;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }
    }
}
