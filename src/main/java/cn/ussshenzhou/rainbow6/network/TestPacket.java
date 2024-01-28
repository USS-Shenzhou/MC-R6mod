package cn.ussshenzhou.rainbow6.network;

import cn.ussshenzhou.rainbow6.util.TeamColor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
public class TestPacket implements CustomPacketPayload {
    public final TeamColor winner;

    public TestPacket(TeamColor winner) {
        this.winner = winner;
    }

    public TestPacket(FriendlyByteBuf buf) {
        winner = buf.readEnum(TeamColor.class);
    }

    @OnlyIn(Dist.CLIENT)
    public void clientHandler(PlayPayloadContext context) {

    }

    public void serverHandler(PlayPayloadContext context) {

    }


    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(winner);
    }

    public static final ResourceLocation ID = new ResourceLocation("r6ms", "test");
    @Override
    public ResourceLocation id() {
        return ID;
    }
}
