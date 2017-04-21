package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public class WrappableBasicPriorityEventListenable<T, W extends PriorityListenable<Consumer<T>>> extends BasicPriorityEventListenable<T> implements WrappablePriorityEventListenable<T, W> {

    private W wrapper;

    public WrappableBasicPriorityEventListenable(W wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public W add(Consumer<T> listener) {
        super.add(listener);
        return wrapper;
    }

    @Override
    public W remove(Consumer<T> listener) {
        super.remove(listener);
        return wrapper;
    }

    @Override
    public W add(Consumer<T> listener, float priority) {
        super.add(listener, priority);
        return wrapper;
    }
}
