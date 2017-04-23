package me.engineone.engine.components.scheduler;

import lombok.NonNull;
import me.engineone.core.component.Component;

import static me.engineone.core.Extensions.runnable;

public class TaskComponent extends Component {
    private final TaskData data;

    public TaskComponent(@NonNull Runnable task, double delay, int times, boolean sync, @NonNull SchedulerComponent scheduler) {
        data = new TaskData(delay, times, sync);
        onEnable(runnable(scheduler::task, task, data));
        onDisable(runnable(scheduler::cancelTask, task));
    }

    public static TaskComponent delay(@NonNull Runnable task, double delay, @NonNull SchedulerComponent scheduler) {
        return repeat(task, delay, 1, false, scheduler);
    }

    public static TaskComponent delay(@NonNull Runnable task, double delay, boolean sync, @NonNull SchedulerComponent scheduler) {
        return repeat(task, delay, 1, sync, scheduler);
    }

    public static TaskComponent repeat(@NonNull Runnable task, double delay, @NonNull SchedulerComponent scheduler) {
        return repeat(task, delay, Integer.MAX_VALUE, false, scheduler);
    }

    public static TaskComponent repeat(@NonNull Runnable task, double delay, boolean sync, @NonNull SchedulerComponent scheduler) {
        return repeat(task, delay, Integer.MAX_VALUE, sync, scheduler);
    }

    public static TaskComponent repeat(@NonNull Runnable task, double delay, int times, @NonNull SchedulerComponent scheduler) {
        return repeat(task, delay, times, false, scheduler);
    }

    public static TaskComponent repeat(@NonNull Runnable task, double delay, int times, boolean sync, @NonNull SchedulerComponent scheduler) {
        return new TaskComponent(task, delay, times, sync, scheduler);
    }

    public boolean isSync() {
        return data.isSync();
    }

    public long getNextTickTime() {
        return data.getNextTickTime();
    }

    public long getLastTickTime() {
        return data.getLastTickTime();
    }

    public double getDelay() {
        return data.getDelay();
    }

    public int getRepeatTimes() {
        return data.getRepeatTimes();
    }

    public void delay(double delay) {
        data.delay(delay);
    }

    public void repeat(int times) {
        data.repeat(times);
    }

    public void sync() {
        data.sync();
    }

    public void async() {
        data.async();
    }
}