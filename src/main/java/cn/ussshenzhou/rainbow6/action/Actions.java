package cn.ussshenzhou.rainbow6.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
public enum Actions {
    PRONE(Prone.class, Prone::new),
    DOWN(Down.class, Down::new);

    final Class<? extends Action> type;
    final Supplier<? extends Action> constructor;

    private Actions(Class<? extends Action> type, Supplier<? extends Action> constructor) {
        this.constructor = constructor;
        this.type = type;
    }

    public static List<Action> getAllNewAction() {
        ArrayList<Action> list = new ArrayList<>();
        Arrays.stream(values()).forEach(a -> list.add(a.constructor.get()));
        return list;
    }

    public Supplier<? extends Action> getConstructor() {
        return constructor;
    }

    public static short indexOf(Action action) {
        return (short) Arrays.stream(values()).toList().indexOf(action.getActionEnum());
    }
}
