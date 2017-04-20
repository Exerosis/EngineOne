package me.engineone.core.holder;

import me.engineone.core.listenable.BasicPriorityListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public class BasicCollectionHolder<T> implements CollectionHolder<T> {

    private PriorityListenable<T> removeListenable = new BasicPriorityListenable<>();
    private PriorityListenable<T> addListenable = new BasicPriorityListenable<>();
    private Set<T> contents = new HashSet<>();

    @Override
    public Collection<T> getContents() {
        return contents;
    }

    @Override
    public PriorityListenable<T> getAddListenable() {
        return addListenable;
    }

    @Override
    public PriorityListenable<T> getRemoveListenable() {
        return removeListenable;
    }
}
