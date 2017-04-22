package me.engineone.core.holder.liveholders;

import me.engineone.core.holder.Holder;
import me.engineone.core.holder.LiveHolder;

import java.util.Iterator;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public class DifferenceHolder<T> implements LiveHolder<T> {
    private Holder<T> primary;
    private Holder<T> secondary;

    public DifferenceHolder(Holder<T> primary, Holder<T> secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    public Holder<T> getPrimary() {
        return primary;
    }

    @Override
    public Holder<T> getSecondary() {
        return secondary;
    }

    @Override
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
        return new Iterator<T>() {
            private final Iterator<T> iterator = getPrimary().iterator();
            private T next = iterator.hasNext() ? iterator.next() : null;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T next = this.next;
                do {
                    if (iterator.hasNext())
                        this.next = iterator.next();
                    else {
                        this.next = null;
                        break;
                    }
                } while (getSecondary().test(this.next));
                return next;
            }
        };
    }
}
