package me.engineone.core.listenable;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityListenable<T> extends Listenable<T> {

    @Override
    default PriorityListenable<T> add(T listener) {
        add(listener, 1);
        return this;
    }

    PriorityListenable<T> add(T listener, float priority);

    @Override
    PriorityListenable<T> remove(T listener);

    float getPriority(T listener);
}
