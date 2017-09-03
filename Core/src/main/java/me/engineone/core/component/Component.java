package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Component implements Enableable {

    private final List<Runnable> enableListenable = new ArrayList<>();
    private final List<Runnable> disableListenable = new ArrayList<>();
    private boolean enabled = false;

    public Component onEnable(Runnable... listeners) {
        if (listeners.length > 1)
            getEnableListenable().addAll(Arrays.asList(listeners));
        else
            getEnableListenable().add(listeners[0]);
        return this;
    }

    public Component onDisable(Runnable... listeners) {
        if (listeners.length > 1)
            getDisableListenable().addAll(Arrays.asList(listeners));
        else
            getDisableListenable().add(listeners[0]);
        return this;
    }

    public Component unregisterEnable(Runnable... listeners) {
        if (listeners.length > 1)
            getEnableListenable().removeAll(Arrays.asList(listeners));
        else
            getEnableListenable().remove(listeners[0]);
        return this;
    }

    public Component unregisterDisable(Runnable... listeners) {
        if (listeners.length > 1)
            getDisableListenable().removeAll(Arrays.asList(listeners));
        else
            getDisableListenable().remove(listeners[0]);
        return this;
    }

    public List<Runnable> getEnableListenable() {
        return enableListenable;
    }

    public List<Runnable> getDisableListenable() {
        return disableListenable;
    }

    @Override
    public Component enable() {
        if (enabled)
            return this;
        enableListenable.forEach(Runnable::run);
        enabled = true;
        return this;
    }

    @Override
    public Component disable() {
        if (!enabled)
            return this;
        disableListenable.forEach(Runnable::run);
        enabled = false;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}