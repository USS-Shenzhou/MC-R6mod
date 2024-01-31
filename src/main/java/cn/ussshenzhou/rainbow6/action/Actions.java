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

    final Class<? extends BaseAction> type;
    final Supplier<? extends BaseAction> constructor;

    private Actions(Class<? extends BaseAction> type, Supplier<? extends BaseAction> constructor) {
        this.constructor = constructor;
        this.type = type;
    }

    public static List<BaseAction> getAllNewAction() {
        ArrayList<BaseAction> list = new ArrayList<>();
        Arrays.stream(values()).forEach(a -> list.add(a.constructor.get()));
        return list;
    }

    public Supplier<? extends BaseAction> getConstructor() {
        return constructor;
    }

    public static short indexOf(BaseAction baseAction) {
        return (short) Arrays.stream(values()).toList().indexOf(baseAction.getActionEnum());
    }
}
