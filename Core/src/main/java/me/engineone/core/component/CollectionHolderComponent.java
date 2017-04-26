package me.engineone.core.component;

import me.engineone.core.holder.CollectionHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

public class CollectionHolderComponent<T> extends ParentComponent implements CollectionHolder<T> {

    private final List<Consumer<T>> removeListeners = new ArrayList<>();
    private final List<Consumer<T>> addListeners = new ArrayList<>();
    private final List<Consumer<T>> removedListeners = new ArrayList<>();
    private final List<Consumer<T>> addedListeners = new ArrayList<>();

    private final Collection<T> contents;

    public CollectionHolderComponent() {
        this(new HashSet<>());
    }

    public CollectionHolderComponent(Collection<T> contents) {
        this.contents = contents;
    }

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return removeListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getRemovedListeners() {
        return removedListeners;
    }

    @Override
    public List<Consumer<T>> getAddedListeners() {
        return addedListeners;
    }
}