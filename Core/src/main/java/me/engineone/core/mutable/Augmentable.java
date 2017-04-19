package me.engineone.core.mutable;

import java.util.Set;
import java.util.function.Consumer;

public interface Augmentable<T> {
    boolean add(T element);

    default boolean add(Iterable<? extends T> elements) {
        boolean added = false;
        for (T element : elements)
            added = added || add(element);
        return added;
    }

    default boolean add(T... elements) {
        boolean added = false;
        for (T element : elements)
            added = added || add(element);
        return added;
    }

    Set<Consumer<T>> getAddListeners();


    default Consumer<T> onAdd(Consumer<T> listener) {
        if (getAddListeners().contains(listener))
            getAddListeners().remove(listener);
        else
            getAddListeners().add(listener);
        return listener;
    }
}
