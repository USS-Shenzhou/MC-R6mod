package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
import cn.ussshenzhou.rainbow6.client.animation.ProneAnimator;
import cn.ussshenzhou.rainbow6.client.input.KeyInputListener;
import cn.ussshenzhou.rainbow6.client.input.ModKeyMappingRegistry;
import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.t88.config.ConfigHelper;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * This file is com.alrex.parcool.common.action.impl.Crawl under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class Prone extends Action {

    public Prone() {
        super(Actions.PRONE);
    }

    @Override
    public boolean canStartInClient(Player player, ActionCapability actionCapability, ByteBuffer startInfo) {
        return (KeyInputListener.CRAWL.isPressed()
                && !player.isInWaterOrBubble()
                && player.onGround()
        );
    }

    @Override
    public boolean canContinueInClient(Player player, ActionCapability actionCapability) {
        return canContinue(ConfigHelper.getConfigRead(Control.class).prone, ModKeyMappingRegistry.CRAWL.isDown(), KeyInputListener.CRAWL.isPressed());
    }

    @Override
    public void onWorkingTickInClient(Player player, ActionCapability actionCapability) {
        AnimationCapability animation = AnimationCapability.get(player);
        if (animation != null && !animation.hasAnimator()) {
            animation.setAnimator(new ProneAnimator());
        }
    }

    @Override
    public void onWorkingTick(Player player, ActionCapability actionCapability) {
        player.setSprinting(false);
        player.setPose(Pose.SWIMMING);
    }
}
