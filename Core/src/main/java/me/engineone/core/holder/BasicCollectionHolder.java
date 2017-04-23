package me.engineone.core.holder;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private List<Consumer<T>> removeListenable = new ArrayList<>();
    private List<Consumer<T>> addListenable = new ArrayList<>();
    private Set<T> contents = new HashSet<>();

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public List<Consumer<T>> getAddListeners() {
        return addListenable;
    }

    @Override
    public List<Consumer<T>> getRemoveListeners() {
        return removeListenable;
    }
}
