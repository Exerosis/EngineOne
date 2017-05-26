package me.engineone.core.observeable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by BinaryBench on 5/25/2017.
 */
public class Observable<T> implements Supplier<T> {

    private T value;
    private List<Consumer<T>> changeListeners = new ArrayList<>();

    public Observable(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
        getChangeListeners().forEach(tConsumer -> tConsumer.accept(value));
    }

    @Override
    public T get() {
        return value;
    }

    public void onChange(Consumer<T> listener) {
        getChangeListeners().add(listener);
    }

    public List<Consumer<T>> getChangeListeners() {
        return changeListeners;
    }
}
