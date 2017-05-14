package me.engineone.engine.components.scheduler;

import me.engineone.core.component.Component;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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

    public TaskBuilder every(int time, TimeUnit unit) {
        return new TaskBuilder(time, unit);
    }

    public TimeBuilder every(int time) {
        return new TimeBuilder(time);
    }

    public UnitBuilder every() {
        return new UnitBuilder();
    }

    public class TaskBuilder {
        private final int time;
        private final TimeUnit unit;
        private Runnable task;
        private boolean sync = false;
        private long startTime;
        private Supplier<Boolean> condition;

        private TaskBuilder(int time, TimeUnit unit) {
            this.time = time;
            this.unit = unit;
        }

        public TaskBuilder run(Runnable... tasks) {
            task = tasks.length > 1 ? () -> {
                for (Runnable task : tasks) task.run();
            } : tasks[0];
            return this;
        }

        public TaskBuilder synchronously() {
            sync ^= true;
            return this;
        }

        public TaskBuilder forNext(int time, TimeUnit unit) {
            condition = () -> startTime + unit.toMillis(time) < System.currentTimeMillis();
            return this;
        }

        public ConditionBuilder forNext(int number) {
            return (ConditionBuilder) (condition = new ConditionBuilder(number));
        }

        public UnitConditionBuilder forNext() {
            return (UnitConditionBuilder) (condition = new UnitConditionBuilder());
        }

        public class ConditionBuilder extends TimeBuilder implements Supplier<Boolean> {
            private boolean times = false;

            private ConditionBuilder(int time) {
                super(time);
            }

            public TaskBuilder times() {
                times ^= true;
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder milliseconds() {
                super.milliseconds();
                startTime = System.currentTimeMillis();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder seconds() {
                super.seconds();
                startTime = System.currentTimeMillis();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder minutes() {
                super.minutes();
                startTime = System.currentTimeMillis();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder hours() {
                super.hours();
                startTime = System.currentTimeMillis();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder days() {
                super.days();
                startTime = System.currentTimeMillis();
                return TaskBuilder.this;
            }

            @Override
            public Boolean get() {
                if (times)
                    return time-- > 0;
                return startTime + unit.toMillis(time) < System.currentTimeMillis();
            }
        }

        public class UnitConditionBuilder extends UnitBuilder implements Supplier<Boolean> {

            @Override
            public TaskBuilder millisecond() {
                super.millisecond();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder second() {
                super.second();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder minute() {
                super.minute();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder hour() {
                super.hour();
                return TaskBuilder.this;
            }

            @Override
            public TaskBuilder day() {
                super.day();
                return TaskBuilder.this;
            }

            @Override
            public Boolean get() {
                return startTime + unit.toMillis(time) < System.currentTimeMillis();
            }
        }
    }

    public class TimeBuilder {
        int time;
        TimeUnit unit = TimeUnit.SECONDS;

        private TimeBuilder(int time) {
            this.time = time;
        }

        //--Plural--
        public TaskBuilder milliseconds() {
            unit = TimeUnit.MILLISECONDS;
            return new TaskBuilder(time, unit);
        }

        public TaskBuilder seconds() {
            unit = TimeUnit.SECONDS;
            return new TaskBuilder(time, unit);
        }

        public TaskBuilder minutes() {
            unit = TimeUnit.MINUTES;
            return new TaskBuilder(time, unit);
        }

        public TaskBuilder hours() {
            unit = TimeUnit.HOURS;
            return new TaskBuilder(time, unit);
        }

        public TaskBuilder days() {
            unit = TimeUnit.DAYS;
            return new TaskBuilder(time, unit);
        }
    }

    public class UnitBuilder {
        private TimeUnit unit = TimeUnit.SECONDS;

        private UnitBuilder() {
        }

        //--Single--
        public TaskBuilder millisecond() {
            unit = TimeUnit.MILLISECONDS;
            return new TaskBuilder(1, unit);
        }

        public TaskBuilder second() {
            unit = TimeUnit.SECONDS;
            return new TaskBuilder(1, unit);
        }

        public TaskBuilder minute() {
            unit = TimeUnit.MINUTES;
            return new TaskBuilder(1, unit);
        }

        public TaskBuilder hour() {
            unit = TimeUnit.HOURS;
            return new TaskBuilder(1, unit);
        }

        public TaskBuilder day() {
            unit = TimeUnit.DAYS;
            return new TaskBuilder(1, unit);
        }
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