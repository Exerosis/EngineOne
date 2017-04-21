package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.listenable.*;

public class Component implements Enableable {
    //TODO maybe make these lists, if removeChild and addChild io is faster that way?
    private final PriorityRunnableListenable enableListenable = new BasicPriorityRunnableListenable();
    private final PriorityRunnableListenable disableListenable = new BasicPriorityRunnableListenable();
    private boolean enabled = false;

    public void addEnableListener(Runnable listener) {
        enableListenable.addListener(listener);
    }

    public void removeEnableListener(Runnable listener) {
        enableListenable.removeListener(listener);
    }

    public void addDisableListener(Runnable listener) {
        disableListenable.addListener(listener);
    }

    public void removeDisableListener(Runnable listener) {
        disableListenable.removeListener(listener);
    }

    public PriorityRunnableListenable getEnableListenable() {
        return enableListenable;
    }

    public PriorityRunnableListenable getDisableListenable() {
        return disableListenable;
    }

    protected <T> void registerToListenable(Listenable<T> listenable, T t) {
        getEnableListenable().addListener(() -> listenable.addListener(t));
        getDisableListenable().addListener(() -> listenable.removeListener(t));
    }

    protected <T> void registerToListenable(PriorityListenable<T> listenable, T t, float priority) {
        getEnableListenable().addListener(() -> listenable.addListener(t, priority));
        getDisableListenable().addListener(() -> listenable.removeListener(t));
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