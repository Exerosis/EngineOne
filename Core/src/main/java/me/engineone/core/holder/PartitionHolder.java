package me.engineone.core.holder;

import java.util.Iterator;
import java.util.function.Predicate;

public interface PartitionHolder<T> extends Holder<T> {
    @Override
    default boolean test(T element) {
        return getFilter().test(element);
    }

    @Override
    default Iterator<T> iterator() {
        return Iterators.filter(getParent().iterator(), this);
    }

    @Override
    default int size() {
        int size = 0;
        for (T t : getParent())
            if (test(t))
                size++;
        return size;
    }

    @Override
    default PartitionHolder<T> partition(Predicate<T> filter) {
        return Holder.super.partition(filter);
    }

    Predicate<T> getFilter();

    Holder<T> getParent();
}