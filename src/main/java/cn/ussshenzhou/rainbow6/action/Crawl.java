package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
import cn.ussshenzhou.rainbow6.client.animation.CrawlAnimator;
import cn.ussshenzhou.rainbow6.client.input.KeyInputListener;
import cn.ussshenzhou.rainbow6.client.input.ModKeyMappingRegistry;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * This file is com.alrex.parcool.common.action.impl.Crawl under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class Crawl extends Action {

    public Crawl() {
        super(Actions.CRAWL);
    }

    @Override
    public boolean canStartInClient(Player player, ActionCapability actionCapability, ByteBuffer startInfo) {
        return (KeyInputListener.CRAWL.isPressed()
                && !player.isInWaterOrBubble()
                && player.isOnGround()
        );
    }

    @Override
    public boolean canContinueInClient(Player player, ActionCapability actionCapability) {
        return ModKeyMappingRegistry.CRAWL.isDown();
    }

    @Override
    public void onWorkingTickInClient(Player player, ActionCapability actionCapability) {
        AnimationCapability animation = AnimationCapability.get(player);
        if (animation != null && !animation.hasAnimator()) {
            animation.setAnimator(new CrawlAnimator());
        }
    }

    @Override
    public void onWorkingTick(Player player, ActionCapability actionCapability) {
        player.setSprinting(false);
        player.setPose(Pose.SWIMMING);
    }
}
