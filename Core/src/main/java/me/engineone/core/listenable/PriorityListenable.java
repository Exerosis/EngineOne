package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityListenable<T> extends Listenable<T> {

    @Override
    default void addListener(T listener) {
        addListener(listener, 1);
    }

    void addListener(T listener, float priority);

    float getPriority(T listener);
}
