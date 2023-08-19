package cn.ussshenzhou.rainbow6.network.onlyto.server;

import cn.ussshenzhou.rainbow6.gun.GunServerHandler;
import cn.ussshenzhou.rainbow6.server.match.ServerMatchManager;
import cn.ussshenzhou.t88.network.annotation.Consumer;
import cn.ussshenzhou.t88.network.annotation.Decoder;
import cn.ussshenzhou.t88.network.annotation.Encoder;
import cn.ussshenzhou.t88.network.annotation.NetPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector2f;

import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class GunShotFirePacket {
    public final float shooterPitch,shooterYaw ;

    public GunShotFirePacket(Vector2f pitchAndYaw) {
        this.shooterYaw = pitchAndYaw.x;
        this.shooterPitch = pitchAndYaw.y;
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

    @Consumer
    public void handler(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            GunServerHandler.shoot(this, context.get());
        }
    }
}
