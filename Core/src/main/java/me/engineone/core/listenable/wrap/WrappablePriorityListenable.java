package me.engineone.core.listenable.wrap;

import me.engineone.core.listenable.PriorityListenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public interface WrappablePriorityListenable<T, W extends PriorityListenable<T>> extends PriorityListenable<T> {

    @Override
    boolean isRegistered(T listener);

    @Override
    W add(T listener, float priority);

    @Override
    W remove(T listener);

    @Override
    float getPriority(T listener);
}