package me.engineone.core.holder;

import me.engineone.core.listenable.BasicListenable;
import me.engineone.core.listenable.Listenable;
import me.engineone.core.mutable.Mutable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class MutablePartitionHolder<T> implements PartitionHolder<T>, MutableHolder<T> {

    private Listenable<T> addListenable = new BasicListenable<>();
    private Listenable<T> removeListenable = new BasicListenable<>();

    MutablePartitionHolder() {
        updater(getParent());
    }

    MutablePartitionHolder updater(Mutable<T> mutable) {
        getParent().addAddListener(element -> {
            if (getFilter().test(element))
                getAddListenable().call(element);
        });

        getParent().addRemoveListener(element -> {
            if (getFilter().test(element))
                getRemoveListenable().call(element);
        });
        return this;
    }

    @Override
    public Listenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public Listenable<T> getRemoveListenable() {
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