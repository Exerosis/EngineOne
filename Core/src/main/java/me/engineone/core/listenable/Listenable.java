package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface Listenable<T> {
    void addListener(Consumer<T> listener);
    void removeListener(Consumer<T> listener);
    boolean isRegistered(Consumer<T> listener);
    void call(T t);
}
