package me.engineone.core.holder.helpers;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 6/1/2017.
 */
public class IDedConsumer<T> extends IDedObject<Consumer<T>> implements Consumer<T> {

    public IDedConsumer(Object id, Consumer<T> wrapped) {
        super(id, wrapped);
    }

    @Override
    public void accept(T t) {
        getWrapped().accept(t);
    }

    @Override
    protected Consumer<T> getSelf() {
        return this;
    }
}
