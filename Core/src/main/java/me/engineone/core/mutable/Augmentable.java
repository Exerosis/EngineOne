package me.engineone.core.mutable;

import me.engineone.core.listenable.EventListenable;

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

    EventListenable<T> getAddListenable();


    default void addAddListener(Consumer<T> listener) {
        getAddListenable().addListener(listener);
    }

    default void removeAddListener(Consumer<T> listener) {
        getAddListenable().removeListener(listener);
    }

}
