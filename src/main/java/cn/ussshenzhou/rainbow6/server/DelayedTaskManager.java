package cn.ussshenzhou.rainbow6.server;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author USS_Shenzhou
 */
public class DelayedTaskManager {
    private static final LinkedList<DelayedTask> TASKS = new LinkedList<>();
    private static final LinkedList<DelayedTask> NEED_ADD = new LinkedList<>();
    private static final LinkedList<DelayedTask> NEED_REMOVE = new LinkedList<>();

    public static void addTask(DelayedTask task) {
        NEED_ADD.add(task);
    }

    public static void removeTask(DelayedTask task){
        NEED_REMOVE.add(task);
    }

    public static void arrange(int lifeTime, Runnable task) {
        addTask(new DelayedTask(lifeTime, task));
    }

    public static void tick() {
        TASKS.addAll(NEED_ADD);
        NEED_ADD.clear();
        NEED_REMOVE.forEach(TASKS::remove);
        NEED_REMOVE.clear();
        Iterator<DelayedTask> it = TASKS.iterator();
        while (it.hasNext()) {
            DelayedTask task = it.next();
            task.tick();
            if (task.isDone()) {
                it.remove();
            }
        }
    }
}
