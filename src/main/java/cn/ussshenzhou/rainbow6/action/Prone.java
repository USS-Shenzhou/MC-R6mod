package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.capability.AnimationCapability;
import cn.ussshenzhou.rainbow6.capability.ModCapabilities;
import cn.ussshenzhou.rainbow6.client.animationplayer.ProneAnimator;
import cn.ussshenzhou.rainbow6.client.input.AnimationPlayerInputListener;
import cn.ussshenzhou.rainbow6.client.input.ModKeyMappingRegistry;
import cn.ussshenzhou.rainbow6.config.Control;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
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
    public boolean toggleStatus = false;

    public Prone() {
        super(Actions.PRONE);
    }

    @Override
    public boolean canStart(Player player, ActionCapability actionCapability, ByteBuffer startInfo) {
        return (AnimationPlayerInputListener.CRAWL.isPressed()
                && !player.isInWaterOrBubble()
                && player.onGround()
        );
    }

    @Override
    public void onClientTick(Player player, ActionCapability capability) {
        if (player.isLocalPlayer()) {
            if (getConfig().prone == KeyTrig.TOGGLE) {
                if (AnimationPlayerInputListener.CRAWL.isPressed()) {
                    toggleStatus = !toggleStatus;
                }
            } else {
                toggleStatus = false;
            }
        }
    }

    @Override
    public boolean canContinue(Player player, ActionCapability actionCapability) {
        //return canContinue(ConfigHelper.getConfigRead(Control.class).prone, ModKeyMappingRegistry.CRAWL.isDown(), AnimationPlayerInputListener.CRAWL.isPressed());
        return ((getConfig().prone == KeyTrig.HOLD && ModKeyMappingRegistry.CRAWL.isDown())
                || (getConfig().prone == KeyTrig.TOGGLE && toggleStatus))
                && !player.onClimbable();
    }

    @Override
    public void onWorkingTickInClient(Player player, ActionCapability actionCapability) {
        AnimationCapability animation = player.getCapability(ModCapabilities.ANIMATION_CAPABILITY);
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
