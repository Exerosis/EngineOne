package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public interface WrapperPriorityListenable<T, W extends PriorityListenable<T>> extends PriorityListenable<T> {

    WrappablePriorityListenable<T, W> getListenable();

    @Override
    default boolean isRegistered(T listener) {
        return getListenable().isRegistered(listener);
    }

    @Override
    default W add(T listener, float priority) {
        return getListenable().add(listener, priority);
    }

    @Override
    default W remove(T listener) {
        return getListenable().remove(listener);
    }

    @Override
    default float getPriority(T listener) {
        return getListenable().getPriority(listener);
    }
}