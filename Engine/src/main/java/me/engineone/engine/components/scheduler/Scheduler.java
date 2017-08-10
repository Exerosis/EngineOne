package me.engineone.engine.components.scheduler;

import me.engineone.core.completeable.Phase;
import org.bukkit.Bukkit;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Scheduler {
    public TaskBuilder every(int time, TimeUnit unit) {
        return new TaskBuilder(time, unit);
    }

    public static TimeBuilder every(int time) {
        return new TimeBuilder(time);
    }

    public static UnitBuilder every() {
        return new UnitBuilder();
    }

    public static class TaskBuilder extends Phase {
        private final int time;
        private final TimeUnit unit;
        private boolean sync = false;
        private long startTime;
        private Supplier<Boolean> condition;
        private Consumer<Integer>[] tasks;
        private int times = 0;
        private static ScheduledExecutorService executor;
        private Predicate<Integer> filter = times -> true;

        private TaskBuilder(int time, TimeUnit unit) {
            this.time = time;
            this.unit = unit;
        }

        public TaskBuilder with(ScheduledExecutorService executor) {
            this.executor = executor;
            return this;
        }

        @SuppressWarnings("unchecked")
        public TaskBuilder run(Runnable... tasks) {
            this.tasks = new Consumer[]{times -> {
                for (Runnable task : tasks)
                    task.run();
            }};
            return this;
        }

        public TaskBuilder filter(Predicate<Integer> filter) {
            this.filter = filter;
            return this;
        }

        public TaskBuilder run(Consumer<Integer>... tasks) {
            this.tasks = tasks;
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

        public UnitConditionBuilder forTheNext() {
            return (UnitConditionBuilder) (condition = new UnitConditionBuilder());
        }

        @Override
        public void enable() {
            if (executor == null || executor.isShutdown() || executor.isTerminated())
                executor = new ScheduledThreadPoolExecutor(10);
            executor.scheduleAtFixedRate(() -> {
                if (filter.test(times++))
                    if (sync)
                        Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugins()[0], () -> {
                            for (Consumer<Integer> task : tasks)
                                task.accept(times);
                            return null;
                        });
                    else
                        for (Consumer<Integer> task : tasks)
                            task.accept(times);
            }, time, time, unit);
        }

        @Override
        public void disable() {
            //TODO Might need to call shutdownNow
            if (executor != null)
                executor.shutdown();
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

    public static class TimeBuilder {
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

    public static class UnitBuilder {
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
}
