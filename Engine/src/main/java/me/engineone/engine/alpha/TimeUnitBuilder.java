package me.engineone.engine.alpha;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TimeUnitBuilder<Parent> extends Builder<TimeUnit> {
    private Parent parent;
    private TimeUnit unit;

    public TimeUnitBuilder(Parent parent) {
        this(parent, TimeUnit.SECONDS);
    }

    public TimeUnitBuilder(Parent parent, TimeUnit unit) {
        this.parent = parent;
        this.unit = unit;
    }

    public Parent milliseconds() {
        unit = TimeUnit.MILLISECONDS;
        build();
        return parent;
    }

    public Parent seconds() {
        unit = TimeUnit.SECONDS;
        build();
        return parent;
    }

    public Parent minutes() {
        unit = TimeUnit.MINUTES;
        build();
        return parent;
    }

    public Parent hours() {
        unit = TimeUnit.HOURS;
        build();
        return parent;
    }

    public Parent days() {
        unit = TimeUnit.DAYS;
        build();
        return parent;
    }

    @Override
    public TimeUnitBuilder onBuild(Consumer<TimeUnit> listener) {
        super.onBuild(listener);
        return this;
    }

    @Override
    public TimeUnit getProduct() {
        return unit;
    }
}