package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.enableable.ParentEnableable;

import java.util.HashSet;
import java.util.Set;

public class ParentComponent extends Component implements ParentEnableable {
    private final Set<Enableable> children = new HashSet<>();

    @Override
    public Set<Enableable> getChildren() {
        return children;
    }

    @Override
    public void enable() {
        super.enable();
        ParentEnableable.super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        ParentEnableable.super.disable();
    }
}