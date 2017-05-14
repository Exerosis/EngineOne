package me.engineone.engine.components.scheduler;

import java.util.concurrent.TimeUnit;

public class Task {
    private int time;
    private TimeUnit unit;
    private final Runnable task;
    private boolean sync = false;


    public Task(int time, TimeUnit unit, Runnable task) {
        this.time = time;
        this.unit = unit;
        this.task = task;
    }


}
