package me.engineone.core.holder.helpers;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class WrappedFilterConsumer<T> implements Consumer<T> {

    private Consumer<T> wrapped;
    private Predicate<T> filter;
    private Object master;

    public WrappedFilterConsumer(Object master, Consumer<T> wrapped, Predicate<T> filter) {
        this.wrapped = wrapped;
        this.filter = filter;
        this.master = master;
    }

    @Override
    public void accept(T t) {
        if (filter.test(t))
            wrapped.accept(t);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WrappedFilterConsumer))
            return false;

        WrappedFilterConsumer other = (WrappedFilterConsumer) obj;

        return wrapped.equals(other.wrapped) && master.equals(other.master);
    }

    @Override
    public int hashCode() {
        return master.hashCode() & wrapped.hashCode();
    }
}