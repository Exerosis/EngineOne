package me.engineone.core.holder;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class ConsumerWasCalled<T> extends RunnableWasCalled implements Consumer<T> {

    @Override
    public void accept(T t) {
        run();
    }
}
