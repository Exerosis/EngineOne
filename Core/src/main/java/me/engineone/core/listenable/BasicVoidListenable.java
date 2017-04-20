package me.engineone.core.listenable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicVoidListenable implements VoidListenable {

    private final Set<Runnable> listeners = new HashSet<>();

    @Override
    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Runnable listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean isRegistered(Runnable listener) {
        return listeners.contains(listener);
    }

    public void call() {
        listeners.forEach(Runnable::run);
    }
}
