package me.engineone.core.completeable;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.enableable.ParentEnableable;

import java.util.HashSet;
import java.util.Set;

import static me.engineone.core.Extensions.runnable;

public class ParentPhase extends Phase implements ParentEnableable {
    private final Set<Enableable> children = new HashSet<>();

    public ParentPhase() {
        onEnable(runnable(System.out::println, "\nEnabled " + getClass().getSimpleName()));
    }

    @Override
    public Set<Enableable> getChildren() {
        return children;
    }

    @Override
    public ParentPhase enable() {
        super.enable();
        ParentEnableable.super.enable();
        return this;
    }

    @Override
    public ParentPhase disable() {
        super.disable();
        ParentEnableable.super.disable();
        return this;
    }
}
