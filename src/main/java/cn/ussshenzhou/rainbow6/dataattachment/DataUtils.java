package cn.ussshenzhou.rainbow6.dataattachment;

import cn.ussshenzhou.rainbow6.action.BaseAction;
import cn.ussshenzhou.rainbow6.action.Actions;
import net.minecraft.world.entity.player.Player;

/**
 * @author USS_Shenzhou
 */
public class DataUtils {

    public static AnimationData getAnimationData(Player player) {
        return player.getData(ModDataAttachments.ANIMATION);
    }

    public static ActionData getActionData(Player player) {
        return player.getData(ModDataAttachments.ACTION);
    }

    public static BaseAction getInstanceOf(ActionData actionData, Actions actionEnum) {
        return actionData.actions.stream().filter(action -> action.getActionEnum() == actionEnum).findFirst().orElse(null);
    }

    public static BaseAction getInstanceOf(Player player, Actions actionEnum) {
        return getActionData(player).actions.stream().filter(action -> action.getActionEnum() == actionEnum).findFirst().orElse(null);
    }
}
