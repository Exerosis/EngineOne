package me.engineone.core.holder;

import me.engineone.core.utilites.IteratorUtil;

import java.util.Iterator;
import java.util.function.Predicate;

public interface PartitionHolder<T> extends Holder<T> {
    @Override
    default boolean test(T element) {
        return getFilter().test(element);
    }

    @Override
    default Iterator<T> iterator() {
        return IteratorUtil.filter(getParent().iterator(), this);
    }

    @Override
    default int size() {
        int size = 0;
        for (T t : getParent())
            if (test(t))
                size++;
        return size;
    }

    Predicate<T> getFilter();

    Holder<T> getParent();
}