package cn.ussshenzhou.rainbow6.server;

/**
 * @author USS_Shenzhou
 */
public class DelayedTask {
    private int age = 0;
    private int runAfter;
    private Runnable task;
    private boolean done = false;

    public DelayedTask(int runAfter, Runnable task) {
        this.runAfter = runAfter;
        this.task = task;
    }

    public void tick() {
        if (age >= runAfter && !done) {
            task.run();
            done = true;
        }
        age++;
    }

    public boolean isDone() {
        return done;
    }
}
