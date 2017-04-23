package me.engineone.engine.components.scheduler;

import me.engineone.core.component.Component;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerComponent extends Component {
    private final Map<Runnable, TaskData> tasks = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public SchedulerComponent() {
        onEnable(() -> {
            if (executor.isShutdown())
                executor = Executors.newFixedThreadPool(10);
            executor.execute(() -> {
                while (isEnabled()) {
                    for (Map.Entry<Runnable, TaskData> entry : tasks.entrySet()) {
                        TaskData taskData = entry.getValue();
                        if (taskData.getNextTickTime() > System.currentTimeMillis())
                            continue;
                        taskData.repeat(taskData.getRepeatTimes() - 1);
                        if (taskData.isSync())
                            Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0], entry.getKey());
                        else
                            executor.execute(entry.getKey());

                        if (taskData.getRepeatTimes() <= 0)
                            tasks.remove(entry.getKey());
                        taskData.lastTickTime = System.currentTimeMillis();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        onDisable(() -> {
            executor.shutdown();
            tasks.clear();
        });
    }

    public Runnable task(Runnable task, boolean sync) {
        if (sync)
            Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0], task);
        else
            executor.execute(task);
        return task;
    }

    public Runnable task(Runnable task, double delay) {
        return task(task, delay, false);
    }

    public Runnable task(Runnable task, double delay, boolean sync) {
        return task(task, delay, 1, sync);
    }

    public Runnable task(Runnable task, double delay, int times) {
        return task(task, delay, times, false);
    }

    public Runnable task(Runnable task, double delay, int times, boolean sync) {
        return task(task, new TaskData(delay, times, sync));
    }

    public Runnable task(Runnable task, TaskData taskData) {
        if (taskData.getRepeatTimes() == 0 && taskData.getDelay() == 0)
            return task(task, taskData.isSync());
        taskData.lastTickTime = System.currentTimeMillis();
        tasks.put(task, taskData);
        return task;
    }

    public TaskData getTaskData(Runnable task) {
        return tasks.get(task);
    }

    public void setTaskData(Runnable task, TaskData taskData) {
        tasks.put(task, taskData);
    }

    public double getTaskDelay(Runnable task) {
        return getTaskData(task).getDelay();
    }

    public int getTaskRepeatTimes(Runnable task) {
        return getTaskData(task).getRepeatTimes();
    }

    public void cancelTask(Runnable task) {
        tasks.remove(task);
    }
}