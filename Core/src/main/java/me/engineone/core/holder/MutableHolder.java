package me.engineone.core.holder;

import me.engineone.core.mutable.Mutable;

import java.util.function.Predicate;

public interface MutableHolder<T> extends Holder<T>, Mutable<T> {
    @Override
    default MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return new MutablePartitionHolder<T>() {
            @Override
            public Predicate<T> getFilter() {
                return filter;
            }

            @Override
            public MutableHolder<T> getParent() {
                return MutableHolder.this;
            }
        };
    }
}