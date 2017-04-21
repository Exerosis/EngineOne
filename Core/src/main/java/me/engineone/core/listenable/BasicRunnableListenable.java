package me.engineone.core.listenable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicRunnableListenable implements RunnableListenable {

    private final Set<Runnable> listeners = new HashSet<>();

    @Override
    public BasicRunnableListenable addListener(Runnable listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public BasicRunnableListenable removeListener(Runnable listener) {
        listeners.remove(listener);
        return this;
    }

    @Override
    public boolean isRegistered(Runnable listener) {
        return listeners.contains(listener);
    }

    public void run() {
        listeners.forEach(Runnable::run);
    }
}
