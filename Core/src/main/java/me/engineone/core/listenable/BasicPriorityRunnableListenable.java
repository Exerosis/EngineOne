package me.engineone.core.listenable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicPriorityRunnableListenable implements PriorityRunnableListenable {

    private Map<Runnable, Float> listeners = new HashMap<>();

    @Override
    public BasicPriorityRunnableListenable add(Runnable listener) {
        return (BasicPriorityRunnableListenable) PriorityRunnableListenable.super.add(listener);
    }

    @Override
    public BasicPriorityRunnableListenable remove(Runnable listener) {
        listeners.remove(listener);
        return this;
    }

    @Override
    public BasicPriorityRunnableListenable add(Runnable listener, float priority) {
        listeners.put(listener, priority);
        return this;
    }

    @Override
    public boolean isRegistered(Runnable listener) {
        return listeners.containsKey(listener);
    }

    @Override
    public float getPriority(Runnable listener) {
        return listeners.get(listener);
    }

    public void run() {
        listeners
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> entry.getKey().run());
    }

}
