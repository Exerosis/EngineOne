package me.engineone.engine.components;

import me.engineone.core.component.ParentComponent;
import me.engineone.engine.components.scheduler.SchedulerComponent;
import me.engineone.engine.utilites.TieBreakers;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static me.engineone.core.Extensions.stream;
import static me.engineone.engine.components.scheduler.TaskComponent.repeat;

public class ActivatorComponent<T> extends ParentComponent {

    public ActivatorComponent(Iterable<T> range, Predicate<T> condition, Consumer<T> action, SchedulerComponent scheduler) {
        this(range, condition, TieBreakers.random(), action, 20, false, scheduler);
    }

    public ActivatorComponent(Iterable<T> range, Predicate<T> condition, Comparator<T> tieBreaker, Consumer<T> action, SchedulerComponent scheduler) {
        this(range, condition, tieBreaker, action, 20, false, scheduler);
    }


    public ActivatorComponent(Iterable<T> range, Predicate<T> condition, Comparator<T> tieBreaker, Consumer<T> action, boolean sync, SchedulerComponent scheduler) {
        this(range, condition, tieBreaker, action, 20, sync, scheduler);
    }

    public ActivatorComponent(Iterable<T> range, Predicate<T> condition, Comparator<T> tieBreaker, Consumer<T> action, int rate, SchedulerComponent scheduler) {
        this(range, condition, tieBreaker, action, rate, false, scheduler);
    }

    public ActivatorComponent(Iterable<T> range, Predicate<T> condition, Comparator<T> tieBreaker, Consumer<T> action, double rate, boolean sync, SchedulerComponent scheduler) {
        addChild(repeat(() -> stream(range).filter(condition).sorted(tieBreaker).sequential().forEachOrdered(action), rate, Integer.MAX_VALUE, sync, scheduler));
    }
}