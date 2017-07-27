package me.engineone.core.component;

import me.engineone.core.holder.single.SingleMutableHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 6/1/2017.
 */
public class SingleHolderComponent<T> extends ParentComponent implements SingleMutableHolder<T> {
    private List<Consumer<T>> addListeners = new ArrayList<>();
    private List<Consumer<T>> removeListeners = new ArrayList<>();
    private List<Consumer<T>> addedListeners = new ArrayList<>();
    private List<Consumer<T>> removedListeners = new ArrayList<>();
    private List<BiConsumer<T, T>> changeListeners = new ArrayList<>();
    private List<BiConsumer<T, T>> changedListeners = new ArrayList<>();

    private T value = null;

    @Override
    public T getValue() {
        return value;
    }
    @Override
    public void setValueSilently(T value) {
        this.value = value;
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
    @Override
    public List<BiConsumer<T, T>> getChangeListeners() {
        return changeListeners;
    }
    @Override
    public List<BiConsumer<T, T>> getChangedListeners() {
        return changedListeners;
    }

}
