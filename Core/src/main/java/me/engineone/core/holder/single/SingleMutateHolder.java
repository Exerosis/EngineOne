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
import java.util.function.Supplier;

/**
 * Created by BinaryBench on 6/1/2017.
 */
public interface SingleMutateHolder<T> extends MutateHolder<T>, Observable<T>, Supplier<T> {


    List<BiConsumer<T, T>> getChangeListeners();
    List<BiConsumer<T, T>> getChangedListeners();

    T getValue();

    @Override
    default int size() {
        if (getValue() == null)
            return 0;
        else
            return 1;
    }
    @Override
    default boolean test(T element) {
        return getValue() != null && getValue().equals(element);
    }
    @Override
    default Iterator<T> iterator() {
        if (getValue() == null)
            return Collections.emptyIterator();
        else
            return Iterators.singletonIterator(getValue());

    }


    default SingleMutateHolder<T> onChange(BiConsumer<T, T> listener) {
        getChangeListeners().add(listener);
        return this;
    }
    default SingleMutateHolder<T> unregisterChange(BiConsumer<T, T> listener) {
        getChangeListeners().remove(listener);
        return this;
    }
    default SingleMutateHolder<T> onChanged(BiConsumer<T, T> listener) {
        getChangedListeners().add(listener);
        return this;
    }
    default SingleMutateHolder<T> unregisterChanged(BiConsumer<T, T> listener) {
        getChangedListeners().remove(listener);
        return this;
    }


    //Observable
    @Override
    default void get(Consumer<T> callback) {
        if (getValue() != null) {
            callback.accept(getValue());
        } else {
            onAdd(new IDedConsumer<T>(this, callback) {
                @Override
                public void accept(T t) {
                    getWrapped().accept(t);
                    unregisterAdd(this);
                }
            });
        }
    }

    @Override
    default void cancel(Consumer<T> callback) {
        unregisterAdd(new IDedConsumer<>(this, callback));
    }

    @Override
    default T get() {
        return getValue();
    }
}
