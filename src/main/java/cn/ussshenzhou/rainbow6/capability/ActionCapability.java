package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.action.Action;
import cn.ussshenzhou.rainbow6.action.Actions;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This file is copied and modified from com.alrex.parcool.common.capability.impl.Parkourability under GPLv3.
 *
 * @author USS_Shenzhou
 */
public class ActionCapability {

    private final List<Action> actions = Actions.getAllNewAction();

    public ActionCapability() {

    }

    public Action getInstanceOf(Actions actionEnum) {
        return actions.stream().filter(action -> action.getActionEnum() == actionEnum).findFirst().orElse(null);
    }

    public List<Action> getActions() {
        return actions;
    }
}
