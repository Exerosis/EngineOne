package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;

import java.util.ArrayList;
import java.util.List;

public class Component implements Enableable {

    private final List<Runnable> enableListenable = new ArrayList<>();
    private final List<Runnable> disableListenable = new ArrayList<>();
    private boolean enabled = false;

    public Component onEnable(Runnable listener) {
        getEnableListenable().add(listener);
        return this;
    }
    public Component unregisterEnable(Runnable listener) {
        getEnableListenable().remove(listener);
        return this;
    }
    public Component onDisable(Runnable listener) {
        getDisableListenable().add(listener);
        return this;
    }
    public Component unregisterDisable(Runnable listener) {
        getDisableListenable().remove(listener);
        return this;
    }

    public List<Runnable> getEnableListenable() {
        return enableListenable;
    }
    public List<Runnable> getDisableListenable() {
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