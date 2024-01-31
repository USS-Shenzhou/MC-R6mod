package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.action.BaseAction;
import cn.ussshenzhou.rainbow6.action.Actions;
import cn.ussshenzhou.rainbow6.dataattachment.ActionData;
import cn.ussshenzhou.rainbow6.dataattachment.DataUtils;
import cn.ussshenzhou.t88.network.NetworkHelper;
import cn.ussshenzhou.t88.network.annotation.ClientHandler;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import cn.ussshenzhou.t88.network.annotation.ServerHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.UUID;

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
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer
                .writeLong(senderUUID.getMostSignificantBits())
                .writeLong(senderUUID.getLeastSignificantBits())
                .writeInt(buffer.length)
                .writeBytes(buffer);
    }

    @cn.ussshenzhou.t88.network.annotation.Decoder
    public SyncActionPacket(FriendlyByteBuf packetBuffer) {
        this.senderUUID = new UUID(packetBuffer.readLong(), packetBuffer.readLong());
        int size = packetBuffer.readInt();
        this.buffer = new byte[size];
        packetBuffer.readBytes(this.buffer);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {
        Player player = context.player().orElse(null);
        if (player == null) {
            return;
        }
        /*player.getLevel().getNearbyPlayers(TargetingConditions.forNonCombat(), player, player.getBoundingBox().inflate(16 * 12))
                .forEach(p -> NetworkHelper.getChannel(SyncActionPacket.class).send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) p), this));*/
        NetworkHelper.sendTo(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(player), this);
        ActionData actionData = DataUtils.getActionData(player);

        Decoder decoder = new Decoder(this.buffer, actionData);
        while (decoder.hasNext()) {
            ActionSyncData item = decoder.getItem();
            if (item == null) {
                continue;
            }
            BaseAction baseAction = item.getAction();
            switch (item.getType()) {
                case START -> {
                    baseAction.setDoing(true);
                    baseAction.onStartInServer(player, actionData, item.getBuffer());
                    baseAction.onStart(player, actionData);
                }
                case FINISH -> {
                    baseAction.setDoing(false);
                    baseAction.onStopInServer(player);
                    baseAction.onStop(player);
                }
                case NORMAL -> baseAction.restoreSynchronizedState(item.getBuffer());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
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
        ActionData actionData = DataUtils.getActionData(player);

        SyncActionPacket.Decoder decoder = new SyncActionPacket.Decoder(this.buffer, actionData);
        while (decoder.hasNext()) {
            SyncActionPacket.ActionSyncData item = decoder.getItem();
            if (item == null) {
                continue;
            }
            BaseAction baseAction = item.getAction();
            switch (item.getType()) {
                case START -> {
                    baseAction.setDoing(true);
                    baseAction.onStartInOtherClient(player, actionData, item.getBuffer());
                    baseAction.onStart(player, actionData);
                }
                case FINISH -> {
                    baseAction.setDoing(false);
                    baseAction.onStopInOtherClient(player);
                    baseAction.onStop(player);
                }
                case NORMAL -> baseAction.restoreSynchronizedState(item.getBuffer());
            }
        }
    }

    public static class Encoder {
        private static final Encoder INSTANCE = new Encoder();

        private Encoder() {
        }

        private final ByteBuffer buffer = ByteBuffer.allocate(1024);

        public static Encoder reset() {
            INSTANCE.buffer.clear();
            return INSTANCE;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Encoder appendSyncData(ActionData actionData, BaseAction baseAction, ByteBuffer actionBuffer) {
            return append(SyncActionPacket.DataType.NORMAL, actionData, baseAction, actionBuffer);
        }

        @SuppressWarnings("UnusedReturnValue")
        public Encoder appendStartData(ActionData actionData, BaseAction baseAction, ByteBuffer actionBuffer) {
            return append(SyncActionPacket.DataType.START, actionData, baseAction, actionBuffer);
        }

        @SuppressWarnings("UnusedReturnValue")
        public Encoder appendFinishMsg(ActionData actionData, BaseAction baseAction) {
            short id = Actions.indexOf(baseAction);
            if (id < 0) {
                return this;
            }
            buffer.putShort(id)
                    .put(SyncActionPacket.DataType.FINISH.getCode())
                    .putInt(0);
            return this;
        }

        private Encoder append(SyncActionPacket.DataType type, ActionData actionData, BaseAction baseAction, ByteBuffer actionBuffer) {
            short id = Actions.indexOf(baseAction);
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
        ActionData actionData;

        Decoder(byte[] buf, ActionData actionData) {
            buffer = ByteBuffer.wrap(buf);
            this.actionData = actionData;
        }

        public boolean hasNext() {
            return buffer.position() < buffer.limit();
        }

        @Nullable
        public SyncActionPacket.ActionSyncData getItem() {
            BaseAction baseAction = DataUtils.getInstanceOf(actionData, Actions.values()[buffer.getShort()]);
            SyncActionPacket.DataType type = SyncActionPacket.DataType.getFromCode(buffer.get());
            int bufferSize = buffer.getInt();
            if (bufferSize > 1024) {
                StringBuilder msgBuilder = new StringBuilder();
                msgBuilder.append("Synchronization failed. demanded buffer size is too large\n")
                        .append(baseAction)
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
            if (baseAction == null) {
                return null;
            }
            ByteBuffer buf = ByteBuffer.wrap(array);
            return new SyncActionPacket.ActionSyncData(baseAction, buf, type);
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
        BaseAction baseAction;
        ByteBuffer buffer;
        DataType type;

        public ActionSyncData(BaseAction baseAction, ByteBuffer buffer, DataType type) {
            this.baseAction = baseAction;
            this.buffer = buffer;
            this.type = type;
        }

        public DataType getType() {
            return type;
        }

        public BaseAction getAction() {
            return baseAction;
        }

        public ByteBuffer getBuffer() {
            return buffer;
        }
    }
}
