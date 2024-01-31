package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.dataattachment.ActionData;
import cn.ussshenzhou.rainbow6.dataattachment.AnimationData;
import cn.ussshenzhou.rainbow6.client.animationplayer.ProneAnimator;
import cn.ussshenzhou.rainbow6.client.input.AnimationPlayerInputListener;
import cn.ussshenzhou.rainbow6.client.input.ModKeyMappings;
import cn.ussshenzhou.rainbow6.dataattachment.DataUtils;
import cn.ussshenzhou.rainbow6.util.KeyTrig;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * This file is com.alrex.parcool.common.action.impl.Crawl under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class Prone extends BaseAction {
    public boolean toggleStatus = false;

    public Prone() {
        super(Actions.PRONE);
    }

    @Override
    public boolean canStart(Player player, ActionData actionData, ByteBuffer startInfo) {
        return (AnimationPlayerInputListener.CRAWL.isPressed()
                && !player.isInWaterOrBubble()
                && player.onGround()
        );
    }

    @Override
    public void onClientTick(Player player, ActionData capability) {
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
    public boolean canContinue(Player player, ActionData actionData) {
        //return canContinue(ConfigHelper.getConfigRead(Control.class).prone, ModKeyMappingRegistry.CRAWL.isDown(), AnimationPlayerInputListener.CRAWL.isPressed());
        return ((getConfig().prone == KeyTrig.HOLD && ModKeyMappings.CRAWL.isDown())
                || (getConfig().prone == KeyTrig.TOGGLE && toggleStatus))
                && !player.onClimbable();
    }

    @Override
    public void onWorkingTickInClient(Player player, ActionData actionData) {
        AnimationData animation = DataUtils.getAnimationData(player);
        if (!animation.hasAnimator()) {
            animation.setAnimator(new ProneAnimator());
        }
    }

    @Override
    public void onWorkingTick(Player player, ActionData actionData) {
        player.setSprinting(false);
        player.setPose(Pose.SWIMMING);
    }
}
