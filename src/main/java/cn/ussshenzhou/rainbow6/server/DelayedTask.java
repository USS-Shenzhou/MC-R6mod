package cn.ussshenzhou.rainbow6.server;

/**
 * @author USS_Shenzhou
 */
public class DelayedTask {
    private int age = 0;
    private int lifeTime;
    private Runnable task;
    private boolean done = false;

    public DelayedTask(int lifeTime, Runnable task) {
        this.lifeTime = lifeTime;
        this.task = task;
    }

    public void tick() {
        if (age >= lifeTime && !done) {
            task.run();
            done = true;
        }
        age++;
    }

    public boolean isDone() {
        return done;
    }
}
