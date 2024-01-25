package cn.ussshenzhou.rainbow6.event;

import net.minecraft.client.OptionInstance;
import net.neoforged.bus.api.Event;

/**
 * @author USS_Shenzhou
 */
public class OptionInstanceChangeEvent<T> extends Event {
    public final OptionInstance<T> optionInstance;
    public final T value;

    public OptionInstanceChangeEvent(OptionInstance<T> optionInstance, T value) {
        this.optionInstance = optionInstance;
        this.value = value;
    }
}
