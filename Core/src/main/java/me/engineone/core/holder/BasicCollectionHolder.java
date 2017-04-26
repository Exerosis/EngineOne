package me.engineone.core.holder;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private List<Consumer<T>> addListeners = new ArrayList<>();
    private List<Consumer<T>> removeListeners = new ArrayList<>();
    private List<Consumer<T>> addedListeners = new ArrayList<>();
    private List<Consumer<T>> removedListeners = new ArrayList<>();

    private Set<T> contents = new HashSet<>();

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public List<Consumer<T>> getAddedListeners() {
        return addedListeners;
    }

    @Override
    public List<Consumer<T>> getRemovedListeners() {
        return removedListeners;
    }
}
