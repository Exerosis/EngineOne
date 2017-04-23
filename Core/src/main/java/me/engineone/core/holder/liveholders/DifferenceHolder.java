package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.Holder;
import me.engineone.core.holder.Iterators;
import me.engineone.core.holder.LiveHolder;

import java.util.Iterator;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public class DifferenceHolder<T> implements Holder<T>{
    private Holder<T> primary;
    private Holder<T> secondary;

    public DifferenceHolder(Holder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }


    public Holder<T> getPrimary() {
        return primary;
    }


    public Holder<T> getSecondary() {
        return secondary;
    }


    public boolean test(T element) {
        return !getPrimary().test(element) && getPrimary().test(element);
    }

    @Override
    public int size() {
        int size = getPrimary().size();
        for (T t : getPrimary())
            if (getSecondary().test(t))
                size--;
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.difference(getPrimary().iterator(), getSecondary());
    }
}
