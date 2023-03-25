package cn.ussshenzhou.rainbow6.capability;

import cn.ussshenzhou.rainbow6.action.Action;
import cn.ussshenzhou.rainbow6.action.Actions;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This file is copied and modified from com.alrex.parcool.common.capability.impl.Parkourability under GPLv3.
 *
 * @author USS_Shenzhou
 */
@AutoRegisterCapability
public class ActionCapability {

    public static @Nullable ActionCapability get(Player player) {
        LazyOptional<ActionCapability> optional = player.getCapability(ModCapabilityRegistry.ACTION_CAPABILITY);
        return optional.orElse(new ActionCapability());
    }

    private final List<Action> actions = Actions.getAllNewAction();

    public ActionCapability() {

    }

    public Action getInstanceOf(Actions actionEnum) {
        return actions.stream().filter(action -> action.getActionEnum() == actionEnum).findFirst().orElse(null);
    }

    public List<Action> getActions() {
        return actions;
    }

    public static class Provider implements ICapabilityProvider {
        private final ActionCapability instance = new ActionCapability();
        private final LazyOptional<ActionCapability> optional = LazyOptional.of(() -> instance);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilityRegistry.ACTION_CAPABILITY.orEmpty(cap, optional);
        }
    }
}
