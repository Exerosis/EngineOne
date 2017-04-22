package me.engineone.core.holder;

import me.engineone.core.listenable.BasicPriorityEventListenable;
import me.engineone.core.listenable.Listenable;
import me.engineone.core.listenable.PriorityEventListenable;
import org.javatuples.Pair;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class MutablePartitionHolder<T> implements PartitionHolder<T>, MutableHolder<T> {

    private PriorityEventListenable<T> addListenable = new BasicPriorityEventListenable<>();
    private PriorityEventListenable<T> removeListenable = new BasicPriorityEventListenable<>();

    private MutableHolder<T> parent;
    private Predicate<T> filter;

    public MutablePartitionHolder(MutableHolder<T> parent, Predicate<T> filter) {
        this(parent, filter, null);
    }

    public MutablePartitionHolder(MutableHolder<T> parent, Pair<Predicate<T>, Listenable<Consumer<T>>> filter) {
        this(parent, filter.getValue0(), filter.getValue1());
    }

    public MutablePartitionHolder(MutableHolder<T> parent, Predicate<T> filter, Listenable<Consumer<T>> updater) {
        this.parent = parent;
        this.filter = filter;
        // Add Parent Listeners
        getParent().addAddListener(element -> {
            if (getFilter().test(element))
                getAddListenable().accept(element);
        });
        getParent().addRemoveListener(element -> {
            if (getFilter().test(element))
                getRemoveListenable().accept(element);
        });
        // Add update listener
        if (updater != null)
            updater.add(element -> {
                if (getParent().test(element))
                    if (getFilter().test(element))
                        getAddListenable().accept(element);
                    else
                        getRemoveListenable().accept(element);
            });
    }

    @Override
    public PriorityEventListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public PriorityEventListenable<T> getRemoveListenable() {
        return removeListenable;
    }

    @Override
    public boolean addSilently(T element) {
        return getParent().addSilently(element);
    }

    @Override
    public boolean removeSilently(T element) {
        return getParent().removeSilently(element);
    }

    @Override
    public MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return MutableHolder.super.partition(filter);
    }

    @Override
    public Predicate<T> getFilter() {
        return filter;
    }

    @Override
    public MutableHolder<T> getParent() {
        return parent;
    }
}