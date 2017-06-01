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
public class StaticObservable<T> implements Observable<T>, Supplier<T> {

    private Supplier<T> wrapped;

    public StaticObservable(T value) {
        this(() -> value);
    }

    public StaticObservable(Supplier<T> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public T get() {
        return wrapped.get();
    }

    @Override
    public void get(Consumer<T> callback) {
        callback.accept(get());
    }

    @Override
    public void cancel(Consumer<T> callback) {
    }


}