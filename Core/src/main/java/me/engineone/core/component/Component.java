package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.listenable.*;

public class Component implements Enableable {

    private final PriorityRunnableListenable enableListenable = new BasicPriorityRunnableListenable();
    private final PriorityRunnableListenable disableListenable = new BasicPriorityRunnableListenable();
    private boolean enabled = false;

    public Component addEnable(Runnable listener) {
        enableListenable.add(listener);
        return this;
    }

    public Component removeEnable(Runnable listener) {
        enableListenable.remove(listener);
        return this;
    }

    public Component addDisable(Runnable listener) {
        disableListenable.add(listener);
        return this;
    }

    public Component removeDisable(Runnable listener) {
        disableListenable.remove(listener);
        return this;
    }

    public PriorityRunnableListenable getEnableListenable() {
        return enableListenable;
    }

    public PriorityRunnableListenable getDisableListenable() {
        return disableListenable;
    }

    protected <T> void registerToListenable(Listenable<T> listenable, T t) {
        getEnableListenable().add(() -> listenable.add(t));
        getDisableListenable().add(() -> listenable.remove(t));
    }

    protected <T> void registerToListenable(PriorityListenable<T> listenable, T t, float priority) {
        getEnableListenable().add(() -> listenable.add(t, priority));
        getDisableListenable().add(() -> listenable.remove(t));
    }

    @Override
    public void enable() {
        if (enabled)
            return;
        enableListenable.run();
        enabled = true;
    }

    @Override
    public void disable() {
        if (!enabled)
            return;
        disableListenable.run();
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}