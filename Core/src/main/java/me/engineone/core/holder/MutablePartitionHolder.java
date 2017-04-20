package me.engineone.core.holder;

import me.engineone.core.listenable.BasicEventListenable;
import me.engineone.core.listenable.EventListenable;
import me.engineone.core.mutable.Mutable;

import java.util.function.Predicate;

public abstract class MutablePartitionHolder<T> implements PartitionHolder<T>, MutableHolder<T> {

    private EventListenable<T> addListenable = new BasicEventListenable<>();
    private EventListenable<T> removeListenable = new BasicEventListenable<>();

    MutablePartitionHolder() {
        updater(getParent());
    }

    MutablePartitionHolder updater(Mutable<T> mutable) {
        getParent().addAddListener(element -> {
            if (getFilter().test(element))
                getAddListenable().accept(element);
        });

        getParent().addRemoveListener(element -> {
            if (getFilter().test(element))
                getRemoveListenable().accept(element);
        });
        return this;
    }

    @Override
    public EventListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public EventListenable<T> getRemoveListenable() {
        return removeListenable;
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