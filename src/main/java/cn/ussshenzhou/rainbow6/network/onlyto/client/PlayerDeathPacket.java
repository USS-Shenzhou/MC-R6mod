package cn.ussshenzhou.rainbow6.network.onlyto.client;

import cn.ussshenzhou.rainbow6.client.gui.hud.AutoCloseHud;
import cn.ussshenzhou.rainbow6.client.gui.hud.PromptHud;
import cn.ussshenzhou.t88.network.annotation.*;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author USS_Shenzhou
 */
@NetPacket
public class PlayerDeathPacket {

    public PlayerDeathPacket() {
    }

    @Decoder
    public PlayerDeathPacket(FriendlyByteBuf buf) {
    }

    @Encoder
    public void write(FriendlyByteBuf buf) {
    }


    @OnlyIn(Dist.CLIENT)
    @ClientHandler
    public void clientHandler(PlayPayloadContext context) {
        AutoCloseHud.ifPresentFormerThenRemoveAndAdd(new PromptHud.AliveAmountHud());
    }

    @ServerHandler
    public void serverHandler(PlayPayloadContext context) {

    }
}
