package me.engineone.thraxpvp;

import me.engineone.core.holder.MutateHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StaticPartitionHolder<T> implements MutateHolder<T> {
    private final MutateHolder<T> parent;
    private final Predicate<T> partition;
    private final List<Consumer<T>> addListeners = new ArrayList<>();
    private final List<Consumer<T>> addedListeners = new ArrayList<>();
    private final List<Consumer<T>> removeListeners = new ArrayList<>();
    private final List<Consumer<T>> removedListeners = new ArrayList<>();
    private int size;
    private MutateHolder<T> inverseHolder;

    public StaticPartitionHolder(MutateHolder<T> parent, Predicate<T> partition) {
        this.parent = parent;
        this.partition = partition;
        parent.onAdd(item -> {
            if (partition.test(item))
                addListeners.forEach(listener -> listener.accept(item));
        });
        parent.onAdded(item -> {
            if (!partition.test(item))
                return;
            size++;
            addedListeners.forEach(listener -> listener.accept(item));
        });
        parent.onRemove(item -> {
            if (partition.test(item))
                removeListeners.forEach(listener -> listener.accept(item));
        });
        parent.onRemoved(item -> {
            if (!partition.test(item))
                return;
            size--;
            removedListeners.forEach(listener -> listener.accept(item));
        });
    }

    public MutateHolder<T> inverse() {
        if (inverseHolder != null)
            return inverseHolder;
        inverseHolder = new MutateHolder<T>() {
            private final List<Consumer<T>> addListeners = new ArrayList<>();
            private final List<Consumer<T>> addedListeners = new ArrayList<>();
            private final List<Consumer<T>> removeListeners = new ArrayList<>();
            private final List<Consumer<T>> removedListeners = new ArrayList<>();

            @Override
            public int size() {
                return parent.size() - size;
            }

            @Override
            public boolean test(T element) {
                return partition.negate().and(parent).test(element);
            }

            @Override
            public Iterator<T> iterator() {
                return parent.stream().filter(partition.negate()).iterator();
            }

            @Override
            public List<Consumer<T>> getAddListeners() {
                return addListeners;
            }

            @Override
            public List<Consumer<T>> getAddedListeners() {
                return addedListeners;
            }

            @Override
            public List<Consumer<T>> getRemoveListeners() {
                return removeListeners;
            }

            @Override
            public List<Consumer<T>> getRemovedListeners() {
                return removedListeners;
            }
        };

        parent.onAdd(item -> {
            if (!partition.test(item))
                inverseHolder.getAddListeners().forEach(listener -> listener.accept(item));
        });
        parent.onAdded(item -> {
            if (partition.test(item))
                return;
            size++;
            inverseHolder.getAddedListeners().forEach(listener -> listener.accept(item));
        });
        parent.onRemove(item -> {
            if (!partition.test(item))
                inverseHolder.getRemoveListeners().forEach(listener -> listener.accept(item));
        });
        parent.onRemoved(item -> {
            if (partition.test(item))
                return;
            size--;
            inverseHolder.getRemovedListeners().forEach(listener -> listener.accept(item));
        });
        return inverseHolder;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean test(T element) {
        return parent.and(partition).test(element);
    }

    @Override
    public Iterator<T> iterator() {
        return parent.stream().filter(partition).iterator();
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getAddedListeners() {
        return addedListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public List<Consumer<T>> getRemovedListeners() {
        return removedListeners;
    }
}
