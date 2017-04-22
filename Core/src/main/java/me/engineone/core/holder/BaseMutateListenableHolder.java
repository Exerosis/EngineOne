package me.engineone.core.holder;

import me.engineone.core.listenable.BasicPriorityEventListenable;
import me.engineone.core.listenable.PriorityEventListenable;

import java.util.Iterator;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public abstract class BaseMutateListenableHolder<T> implements MutateListenableHolder<T> {

    private PriorityEventListenable<T> addListenable = new BasicPriorityEventListenable<>();
    private PriorityEventListenable<T> removeListenable = new BasicPriorityEventListenable<>();

    public BaseMutateListenableHolder() {
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