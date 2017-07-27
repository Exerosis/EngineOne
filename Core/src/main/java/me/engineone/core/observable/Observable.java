package me.engineone.core.observable;

import me.engineone.core.component.Component;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 5/31/2017.
 */
public interface Observable<T> {

    public void get(Consumer<T> callback);

    public void cancel(Consumer<T> callback);

    public static <T> Component observe(Observable<T> observable, Consumer<T> callback) {
        Component component = new Component();
        component.onEnable(() -> observable.get(callback));
        component.onDisable(() -> observable.cancel(callback));
        return component;
    }

}
