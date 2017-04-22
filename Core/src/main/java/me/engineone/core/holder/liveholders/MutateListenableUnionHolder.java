package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.*;
import me.engineone.core.listenable.BasicPriorityEventListenable;
import me.engineone.core.listenable.PriorityEventListenable;

import java.util.Iterator;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class MutateListenableUnionHolder<T> extends BaseMutateListenableHolder<T> implements LiveHolder<T> {

    private MutateListenableHolder<T> primary;
    private Holder<T> secondary;

    public MutateListenableUnionHolder(MutateListenableHolder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;

        // Add Parent Listeners
        getPrimary().addAddListener(element -> {
            if (!getSecondary().test(element))
                getAddListenable().accept(element);

        });
        getPrimary().addRemoveListener(element -> {
            if (!getSecondary().test(element))
                getRemoveListenable().accept(element);
        });

        if (secondary instanceof MutateListenableHolder) {
            MutateListenableHolder<T> mute = (MutateListenableHolder<T>) secondary;
            mute.addAddListener(element -> {
                if (!getPrimary().test(element))
                    getAddListenable().accept(element);
            });
            mute.addRemoveListener(element -> {
                if (!getPrimary().test(element))
                    getRemoveListenable().accept(element);
            });
        }

    }

    @Override
    public MutateListenableHolder<T> getPrimary() {
        return primary;
    }

    @Override
    public Holder<T> getSecondary() {
        return secondary;
    }

    @Override
    public boolean test(T element) {
        return getPrimary().test(element) || getSecondary().test(element);
    }

    @Override
    public int size() {
        int size = getSecondary().size();
        for (T t : getPrimary())
            if (!getSecondary().test(t))
                size++;
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.union(getPrimary().iterator(), getSecondary().iterator());
    }
}