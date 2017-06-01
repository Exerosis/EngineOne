package me.engineone.core.observable;

import me.engineone.core.*;
import me.engineone.core.component.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by BinaryBench on 5/31/2017.
 */
public class SettableObservable<T> implements Observable<T>, Supplier<T> {

    private List<Consumer<T>> listeners = new ArrayList<>();

    private final T defaultValue;
    private T value;

    public SettableObservable() {
        this(null);
    }

    public SettableObservable(T defaultValue) {
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        listeners.forEach(listener -> listener.accept(value));
        listeners.clear();
    }

    public void reset() {
        this.value = defaultValue;
        listeners.clear();
    }

    @Override
    public void get(Consumer<T> callback) {
        if (!get().equals(defaultValue)) {
            callback.accept(get());
        } else {
            listeners.add(callback);
        }

    }

    @Override
    public void cancel(Consumer<T> callback) {
        listeners.remove(callback);
    }
}
