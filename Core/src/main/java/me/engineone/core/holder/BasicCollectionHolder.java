package me.engineone.core.holder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private final Set<Consumer<T>> removeListeners = new HashSet<>();
    private final Set<Consumer<T>> addListeners = new HashSet<>();
    private final Set<T> contents = new HashSet<>();

    @Override
    public Set<Consumer<T>> getRemoveListeners() {
        return removeListeners;
    }

    @Override
    public Set<Consumer<T>> getAddListeners() {
        return addListeners;
    }

    @Override
    public Collection<T> getContents() {
        return contents;
    }
}
