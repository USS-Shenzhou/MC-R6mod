package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.gun.GunServerHandler;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class GunShotFirePacket {
    public final float shooterPitch, shooterYaw;

    public GunShotFirePacket(float pitch, float yaw) {
        this.shooterYaw = pitch;
        this.shooterPitch = yaw;
    }

    @Decoder
    public GunShotFirePacket(FriendlyByteBuf buf) {
        this.shooterPitch = buf.readFloat();
        this.shooterYaw = buf.readFloat();
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(shooterPitch);
        buf.writeFloat(shooterYaw);
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context){
        GunServerHandler.shoot(this, context);
    }

    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {

    }
}
