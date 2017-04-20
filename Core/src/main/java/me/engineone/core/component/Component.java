package me.engineone.core.component;

import me.engineone.core.enableable.Enableable;
import me.engineone.core.listenable.BasicListenable;
import me.engineone.core.listenable.Listenable;

import java.util.Set;

public class Component implements Enableable {
    //TODO maybe make these lists, if removeChild and addChild io is faster that way?
    private final Listenable enableListenable = new BasicListenable();
    private final Listenable disableListenable = new BasicListenable();
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

    public Listenable getEnableListenable() {
        return enableListenable;
    }

    public Listenable getDisableListenable() {
        return disableListenable;
    }

    @Override
    public void enable() {
        if (enabled)
            return;
        enableListenable.call();
        enabled = true;
    }

    @Override
    public void disable() {
        if (!enabled)
            return;
        disableListenable.call();
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}