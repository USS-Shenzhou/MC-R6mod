package cn.ussshenzhou.rainbow6.armors;

import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class R6ArmorEvent {
    @SubscribeEvent
    public static void removePlayerWear(RenderPlayerEvent.Pre event) {
        boolean flag = false;
        for (ItemStack itemStack : event.getPlayer().getArmorInventoryList()) {
            if (itemStack.getItem() instanceof R6ArmorItem) {
                flag = true;
                break;
            }
        }
        if (flag) {
            PlayerModel<?> playerModel = event.getRenderer().getEntityModel();
            playerModel.bipedHeadwear.showModel = false;
            playerModel.bipedBodyWear.showModel = false;
            playerModel.bipedLeftArmwear.showModel = false;
            playerModel.bipedRightArmwear.showModel = false;
            playerModel.bipedLeftLegwear.showModel = false;
            playerModel.bipedRightLegwear.showModel = false;
        }
    }
    @SubscribeEvent
    public static void cancelPlayerSkin(RenderPlayerEvent.Pre event){
        for (ItemStack itemStack : event.getPlayer().getArmorInventoryList()) {
            PlayerModel<?> playerModel = event.getRenderer().getEntityModel();
            if (itemStack.getItem() instanceof R6ArmorItem) {
                switch (((R6ArmorItem) itemStack.getItem()).slot){
                    case HEAD:
                        playerModel.bipedHead.showModel = false;
                        break;
                    case CHEST:
                        playerModel.bipedBody.showModel = false;
                        playerModel.bipedLeftArm.showModel = false;
                        playerModel.bipedRightArm.showModel = false;
                        break;
                    case LEGS:
                        playerModel.bipedLeftLeg.showModel = false;
                        playerModel.bipedRightLeg.showModel = false;
                        break;
                    case FEET:
                    default:
                        break;
                }
            }
        }
    }
}
