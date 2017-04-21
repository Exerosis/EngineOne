package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityListenable<T> extends Listenable<T> {

    @Override
    default PriorityListenable<T> addListener(T listener) {
        addListener(listener, 1);
        return this;
    }

    PriorityListenable<T> addListener(T listener, float priority);

    @Override
    PriorityListenable<T> removeListener(T listener);

    float getPriority(T listener);
}
