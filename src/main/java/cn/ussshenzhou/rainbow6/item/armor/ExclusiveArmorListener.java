package cn.ussshenzhou.rainbow6.item.armor;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ExclusiveArmorListener {

    @SubscribeEvent
    public static void hideOriginal(RenderPlayerEvent.Pre event) {
        //FIXME need improve
        var player = event.getEntity();
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        int i = 0;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() instanceof BaseR6ArmorItem r6Armor) {
                setByType(r6Armor.getType(), model, true);
            } else {
                setByType(ArmorItem.Type.values()[i], model, false);
            }
            i++;
        }
    }

    private static void setByType(ArmorItem.Type type, PlayerModel<AbstractClientPlayer> model, boolean value) {
        switch (type) {
            case HELMET -> setByTypeInternal(value, model.head, model.hat, model.ear);
            case CHESTPLATE ->
                    setByTypeInternal(value, model.body, model.leftArm, model.leftSleeve, model.rightArm, model.rightSleeve, model.jacket, model.cloak);
            case LEGGINGS -> setByTypeInternal(value, model.leftLeg, model.leftPants, model.rightLeg, model.rightPants);
            case BOOTS -> {
            }
        }
    }

    private static void setByTypeInternal(boolean value, ModelPart... modelParts) {
        Arrays.stream(modelParts).forEach(modelPart -> modelPart.skipDraw = value);
    }

}
