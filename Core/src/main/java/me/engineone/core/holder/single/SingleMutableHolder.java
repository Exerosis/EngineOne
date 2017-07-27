package me.engineone.core.holder.single;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import me.engineone.core.holder.MutateHolder;
import me.engineone.core.holder.helpers.IDedConsumer;
import me.engineone.core.observable.Observable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 6/1/2017.
 */
public interface SingleMutableHolder<T> extends SingleMutateHolder<T> {

    void setValueSilently(T value);

    default void setValue(T newValue) {
        T oldValue = getValue();

        if (oldValue == newValue || (oldValue != null && oldValue.equals(newValue)))
            return;

        ImmutableList.copyOf(getChangeListeners()).forEach(listener -> listener.accept(oldValue, newValue));

        if (oldValue != null)
            ImmutableList.copyOf(getRemoveListeners()).forEach(listener -> listener.accept(oldValue));
        if (newValue != null)
            ImmutableList.copyOf(getAddListeners()).forEach(listener -> listener.accept(newValue));

        setValueSilently(newValue);

        if (oldValue != null)
            ImmutableList.copyOf(getRemovedListeners()).forEach(listener -> listener.accept(oldValue));
        if (newValue != null)
            ImmutableList.copyOf(getAddedListeners()).forEach(listener -> listener.accept(newValue));

        ImmutableList.copyOf(getChangeListeners()).forEach(listener -> listener.accept(oldValue, newValue));
    }


}
