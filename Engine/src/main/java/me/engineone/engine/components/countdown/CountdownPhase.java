package me.engineone.engine.components.countdown;

import me.engineone.core.completeable.ParentPhase;
import me.engineone.core.completeable.Phase;
import me.engineone.engine.utilites.scheduler.SyncRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 5/23/2017.
 */
public class CountdownPhase extends ParentPhase {

    protected List<Consumer<Integer>> countListeners = new ArrayList<>();

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureTask;

    private int count;
    private long period;
    private TimeUnit timeUnit;

    public CountdownPhase(ScheduledExecutorService scheduler, int count) {
        this(scheduler, count, 1, TimeUnit.SECONDS);
    }

    public CountdownPhase(ScheduledExecutorService scheduler, int count, long period, TimeUnit timeUnit) {
        this.scheduler = scheduler;
        this.count = count;
        this.period = period;
        this.timeUnit = timeUnit;

        onEnable(this::start);
        onDisable(this::stop);
    }

    public boolean start() {
        if (isRunning())
            return false;
        futureTask = getScheduler().scheduleAtFixedRate((SyncRunnable) () -> {

            getCountListeners().forEach(integerConsumer -> integerConsumer.accept(getCount()));
            count--;
            if (count <= 0) {
                stop();
                complete();
            }
        }, getPeriod(), getPeriod(), getTimeUnit());
        return true;
    }

    public boolean stop() {
        if (!isRunning())
            return false;
        futureTask.cancel(true);
        futureTask = null;
        return true;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isRunning() {
        return futureTask != null && !futureTask.isCancelled();
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public long getPeriod() {
        return period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void onCount(Consumer<Integer> listener) {
        countListeners.add(listener);
    }

    public List<Consumer<Integer>> getCountListeners() {
        return countListeners;
    }
}
