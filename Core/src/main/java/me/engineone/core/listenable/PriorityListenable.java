package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityListenable<T> extends Listenable<T> {

    default void addListener(Consumer<T> listener) {
        addListener(listener, 1);
    }

    void removeListener(Consumer<T> listener);
    boolean isRegistered(Consumer<T> listener);

    void addListener(Consumer<T> listener, float priority);

    float getPriority(Consumer<T> listener);
}
