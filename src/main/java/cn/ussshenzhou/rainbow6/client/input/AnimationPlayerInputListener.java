package cn.ussshenzhou.rainbow6.client.input;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import net.minecraft.client.KeyMapping;

import java.util.LinkedHashMap;

/**
 * @author USS_Shenzhou
 */
public class AnimationPlayerInputListener {
    public static final KeyState CRAWL = new KeyState();

    public static final LinkedHashMap<KeyMapping, KeyState> KEY_STATES = new LinkedHashMap<>() {{
        put(ModKeyMappingRegistry.CRAWL, CRAWL);
    }};

    public static void tick() {
        KEY_STATES.forEach(AnimationPlayerInputListener::recordInMatch);
    }

    private static void recordInMatch(KeyMapping keyBinding, KeyState state) {
        if (!ClientMatch.isInMatch()) {
            return;
        }
        record(keyBinding, state);
    }

    private static void record(KeyMapping keyBinding, KeyState state) {
        state.pressed = (keyBinding.isDown() && state.tickKeyDown == 0);
        state.released = (!keyBinding.isDown() && state.tickNotKeyDown == 0);
        state.doubleTapped = (keyBinding.isDown() && 0 < state.tickNotKeyDown && state.tickNotKeyDown <= 2);
        if (keyBinding.isDown()) {
            state.tickKeyDown++;
            state.tickNotKeyDown = 0;
        } else {
            state.tickKeyDown = 0;
            state.tickNotKeyDown++;
        }
    }

    public static class KeyState {
        private boolean pressed = false;
        private boolean released = false;
        private boolean doubleTapped = false;
        private int tickKeyDown = 0;
        private int tickNotKeyDown = 0;

        public boolean isPressed() {
            return pressed;
        }

        public boolean isReleased() {
            return released;
        }

        public boolean isDoubleTapped() {
            return doubleTapped;
        }

        public int getTickKeyDown() {
            return tickKeyDown;
        }

        public int getTickNotKeyDown() {
            return tickNotKeyDown;
        }
    }
}
