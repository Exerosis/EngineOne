package me.engineone.core.holder;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class ConsumerCallCounter<T> extends RunnableCallCounter implements Consumer<T> {

    @Override
    public void accept(T t) {
        run();
    }

}
