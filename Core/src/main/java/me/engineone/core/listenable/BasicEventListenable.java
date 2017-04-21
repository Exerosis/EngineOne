package me.engineone.core.listenable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicEventListenable<T> implements EventListenable<T> {
    private final Set<Consumer<T>> listeners = new HashSet<>();

    @Override
    public BasicEventListenable<T> addListener(Consumer<T> listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public BasicEventListenable<T> removeListener(Consumer<T> listener) {
        listeners.remove(listener);
        return this;
    }

    @Override
    public boolean isRegistered(Consumer<T> listener) {
        return listeners.contains(listener);
    }

    public void accept(T t) {
        listeners.forEach(listener -> listener.accept(t));
    }
}
