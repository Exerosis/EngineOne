package me.engineone.engine.components.scheduler;

/**
 * Written by Exerosis!
 *
 * @author BlockServer TeamImplementation
 * @see SchedulerComponent
 */
public class TaskData {
    protected long lastTickTime;
    private double delay;
    private int times;
    private boolean sync;

    public TaskData(double delay, int times, boolean sync) {
        this.delay = delay;
        this.times = times;
        this.sync = sync;
    }

    public boolean isSync() {
        return sync;
    }

    public long getNextTickTime() {
        return lastTickTime + (long) delay;
    }

    public long getLastTickTime() {
        return lastTickTime;
    }

    public double getDelay() {
        return delay;
    }

    public int getRepeatTimes() {
        return times;
    }

    public void delay(double delay) {
        this.delay = delay;
    }

    public void repeat(int times) {
        this.times = times;
    }

    public void sync() {
        sync = true;
    }

    public void async() {
        sync = false;
    }

    /*public static TaskData taskData(double delay) {
        return taskData(delay, 1, false);
    }

    public static TaskData taskData(double delay, boolean sync) {
        return taskData(delay, 1, sync);
    }

    public static TaskData taskData(double delay, int times) {
        return taskData(delay, times, false);
    }

    public static TaskData taskData(double delay, int times, boolean sync) {
        return new TaskData(delay, times, sync);
    }*/
}