package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;

import java.util.HashSet;
import java.util.Set;

public class Component implements Enableable {
    //TODO maybe make these lists, if removeChild and addChild io is faster that way?
    private final Set<Runnable> enableListeners = new HashSet<>();
    private final Set<Runnable> disableListeners = new HashSet<>();
    private boolean enabled = false;

    public Runnable onEnable(Runnable listener) {
        if (enableListeners.contains(listener))
            enableListeners.remove(listener);
        else
            enableListeners.add(listener);
        return listener;
    }

    public Runnable onDisable(Runnable listener) {
        if (disableListeners.contains(listener))
            disableListeners.remove(listener);
        else
            disableListeners.add(listener);
        return listener;
    }

    public Set<Runnable> getEnableListeners() {
        return enableListeners;
    }

    public Set<Runnable> getDisableListeners() {
        return disableListeners;
    }

    @Override
    public void enable() {
        if (enabled)
            return;
        enableListeners.forEach(Runnable::run);
        enabled = true;
    }

    @Override
    public void disable() {
        if (!enabled)
            return;
        disableListeners.forEach(Runnable::run);
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}