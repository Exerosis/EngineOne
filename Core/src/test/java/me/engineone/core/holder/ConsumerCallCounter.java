package me.engineone.core.holder;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class ConsumerCallCounter<T> implements Consumer<T> {
    private int counter = 0;

    @Override
    public void accept(T t) {
        counter++;
    }

    public int count() {
        return counter;
    }
}
