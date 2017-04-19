package me.engineone.core.holder;

import me.engineone.core.mutable.Mutable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class MutablePartitionHolder<T> implements PartitionHolder<T>, MutableHolder<T> {
    private Set<Consumer<T>> addListeners = new HashSet<>();
    private Set<Consumer<T>> removeListeners = new HashSet<>();

    public MutablePartitionHolder() {
        updater(getParent());
    }

    public MutablePartitionHolder updater(Mutable<T> mutable) {
        getParent().onAdd(element -> {
            if (getFilter().test(element))
                getAddListeners().forEach(listener -> listener.accept(element));
        });

        getParent().onRemove(element -> {
            if (getFilter().test(element))
                getRemoveListeners().forEach(listener -> listener.accept(element));
        });
        return this;
    }


    @Override
    public Set<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public Set<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public boolean add(T element) {
        return getParent().add(element);
    }

    @Override
    public boolean remove(Object element) {
        return getParent().remove(element);
    }

    @Override
    public MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return MutableHolder.super.partition(getFilter().and(filter));
    }

    @Override
    public abstract MutableHolder<T> getParent();
}