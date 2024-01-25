package cn.ussshenzhou.rainbow6.gun;

import cn.ussshenzhou.rainbow6.gun.entity.TestBulletEntity;
import cn.ussshenzhou.rainbow6.gun.item.TestGun;
import cn.ussshenzhou.rainbow6.network.onlyto.server.GunShotFirePacket;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.NetworkEvent;

/**
 * @author USS_Shenzhou
 */
public class GunServerHandler {

    public static void shoot(GunShotFirePacket packet, NetworkEvent.Context context) {
        ServerPlayer sender = context.getSender();
        if (sender == null || sender.isSpectator()) {
            return;
        }
        var itemStack = sender.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() instanceof TestGun gun) {
            if (!gun.hasAmmoToShoot(itemStack)) {
                //TODO broadcast failed sound
                return;
            }
            //sender.setXRot(packet.shooterPitch);
            //sender.setYRot(packet.shooterYaw);
            var property = gun.getFixedProperty();
            var level = sender.level();
            if (!sender.isCreative() || R6Constants.TEST) {
                gun.consumeOneAmmo(itemStack);
            }
            if (property.pellets() == 1) {
                //aimed and normal 1 bullet weapon
                TestBulletEntity bullet = new TestBulletEntity(level, sender, gun, gun.getModifier(itemStack));
                level.addFreshEntity(bullet);
                bullet.tick();
            } else {
                //un-aimed or shotgun
                //TODO spread
            }
            //TODO broadcast sound
        }
    }
}
