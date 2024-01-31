package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.dataattachment.ActionData;
import cn.ussshenzhou.rainbow6.dataattachment.AnimationData;
import cn.ussshenzhou.rainbow6.dataattachment.DataUtils;
import cn.ussshenzhou.rainbow6.client.animationplayer.ProneAnimator;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * @author USS_Shenzhou
 */
public class Down extends BaseAction {

    public Down() {
        super(Actions.DOWN);
    }

    @Override
    public boolean canStart(Player player, ActionData actionData, ByteBuffer startInfo) {
        return (ClientMatch.isDown());
    }

    @Override
    public boolean canContinue(Player player, ActionData actionData) {
        return ClientMatch.isDown();
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
