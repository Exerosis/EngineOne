package me.engineone.core.holder.helpers;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class IDedFilterConsumer<T> extends IDedObject<Consumer<T>> implements Consumer<T> {

    private Predicate<T> filter;

    public IDedFilterConsumer(Object master, Consumer<T> wrapped) {
        this(master, wrapped, null);
    }

    public IDedFilterConsumer(Object id, Consumer<T> wrapped, Predicate<T> filter) {
        super(id, wrapped);
        this.filter = filter;
    }

    @Override
    public void accept(T t) {
        if (filter.test(t))
            getWrapped().accept(t);
    }

    public Predicate<T> getFilter() {
        return filter;
    }

    @Override
    protected Consumer<T> getSelf() {
        return this;
    }
}