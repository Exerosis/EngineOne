package me.engineone.core.listenable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicListenable<T> implements Listenable<T> {
    private final Set<Consumer<T>> listeners = new HashSet<>();

    @Override
    public void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean isRegistered(Consumer<T> listener) {
        return listeners.contains(listener);
    }

    public void call(T t) {
        listeners.forEach(listener -> listener.accept(t));
    }
}
