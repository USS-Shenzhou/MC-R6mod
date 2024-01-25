package cn.ussshenzhou.rainbow6.action;

import cn.ussshenzhou.rainbow6.capability.ActionCapability;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.nio.ByteBuffer;

/**
 * @author USS_Shenzhou
 */
public class Down extends Action {

    public Down() {
        super(Actions.DOWN);
    }

    @Override
    public boolean canStartInClient(Player player, ActionCapability actionCapability, ByteBuffer startInfo) {
        return (ClientMatch.isDown());
    }

    @Override
    public boolean canContinueInClient(Player player, ActionCapability actionCapability) {
        return ClientMatch.isDown();
    }

    @Override
    public void onWorkingTickInClient(Player player, ActionCapability actionCapability) {
        //TODO update
        /*AnimationCapability animation = AnimationCapability.get(player);
        if (animation != null && !animation.hasAnimator()) {
            animation.setAnimator(new ProneAnimator());
        }*/
    }

    @Override
    public void onWorkingTick(Player player, ActionCapability actionCapability) {
        player.setSprinting(false);
        player.setPose(Pose.SWIMMING);
    }
}
