package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.enableable.ParentEnableable;

import java.util.HashSet;
import java.util.Set;

public class ParentComponent extends Component implements ParentEnableable {
    private final Set<Enableable> children = new HashSet<>();

    public ParentComponent(Enableable... children) {
        addChild(children);
    }

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

    @Override
    public ParentComponent onEnable(Runnable listener) {
        return (ParentComponent) super.onEnable(listener);
    }

    @Override
    public ParentComponent unregisterEnable(Runnable listener) {
        return (ParentComponent) super.unregisterEnable(listener);
    }

    @Override
    public ParentComponent onDisable(Runnable listener) {
        return (ParentComponent) super.onDisable(listener);
    }

    @Override
    public ParentComponent unregisterDisable(Runnable listener) {
        return (ParentComponent) super.unregisterDisable(listener);
    }
}