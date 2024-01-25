package cn.ussshenzhou.rainbow6.client.animationplayer;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import net.minecraft.world.entity.player.Player;

/**
 * This file is copied and modified from com.alrex.parcool.client.animation.impl.CrawlAnimator under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class ProneAnimator extends Animator {
    @Override
    public boolean shouldRemoved(Player player, ActionCapability actionCapability) {
        //TODO update
        //return !actionCapability.getInstanceOf(Actions.PRONE).isDoing();
        return true;
    }

    @Override
    public void animatePost(Player player, ActionCapability actionCapability, PlayerModelTransformer transformer) {
        float factor = (float) Math.sin(transformer.getLimbSwing() / 10 * Math.PI);
        transformer
                .rotateLeftArm((float) Math.toRadians(-15), 0, (float) Math.toRadians(-120 - 25 * factor))
                .rotateRightArm((float) Math.toRadians(-15), 0, (float) Math.toRadians(120 - 25 * factor))
                .rotateLeftLeg((float) Math.toRadians(-8 + 8 * factor), 0, (float) Math.toRadians(-5 + 5 * factor))
                .rotateRightLeg((float) Math.toRadians(-8 - 8 * factor), 0, (float) Math.toRadians(5 + 5 * factor))
                .end();
    }
}
