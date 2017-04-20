package me.engineone.core.holder;

import me.engineone.core.listenable.BasicPriorityEventListenable;
import me.engineone.core.listenable.PriorityEventListenable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private PriorityEventListenable<T> removeListenable = new BasicPriorityEventListenable<>();
    private PriorityEventListenable<T> addListenable = new BasicPriorityEventListenable<>();
    private Set<T> contents = new HashSet<>();

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public PriorityEventListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public PriorityEventListenable<T> getRemoveListenable() {
        return removeListenable;
    }
}
