package me.engineone.core.listenable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicPriorityRunnableListenable implements PriorityRunnableListenable {

    private Map<Runnable, Float> listeners = new HashMap<>();

    @Override
    public void removeListener(Runnable listener) {
        listeners.remove(listener);
    }

    @Override
    public void addListener(Runnable listener, float priority) {
        listeners.put(listener, priority);
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
