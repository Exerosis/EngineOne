package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;

import java.util.ArrayList;
import java.util.List;

public class Component implements Enableable {

    private final List<Runnable> enableListenable = new ArrayList<>();
    private final List<Runnable> disableListenable = new ArrayList<>();
    private boolean enabled = false;

    public Component onEnable(Runnable listener) {
        getEnableListeners().add(listener);
        return this;
    }
    public Component unregisterEnable(Runnable listener) {
        getEnableListeners().remove(listener);
        return this;
    }
    public Component onDisable(Runnable listener) {
        getDisableListeners().add(listener);
        return this;
    }
    public Component unregisterDisable(Runnable listener) {
        getDisableListeners().remove(listener);
        return this;
    }

    public List<Runnable> getEnableListeners() {
        return enableListenable;
    }
    public List<Runnable> getDisableListeners() {
        return disableListenable;
    }

    @Override
    public void enable() {
        if (enabled)
            return;
        enableListenable.forEach(Runnable::run);
        enabled = true;
    }

    @Override
    public void disable() {
        if (!enabled)
            return;
        disableListenable.forEach(Runnable::run);
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}