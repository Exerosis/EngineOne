package me.engineone.core.mutable;

import me.engineone.core.listenable.EventListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.function.Consumer;

public interface Augmentable<T> {
    boolean add(T element);

    default boolean add(Iterable<? extends T> elements) {
        boolean added = false;
        for (T element : elements)
            added = added || add(element);
        return added;
    }

    @SuppressWarnings("unchecked")
    default boolean add(T... elements) {
        boolean added = false;
        for (T element : elements)
            added = added || add(element);
        return added;
    }

    EventListenable<T> getAddListenable();


    default PriorityListenable<Consumer<T>> addAddListener(Consumer<T> listener) {
        getAddListenable().add(listener);
        return null;
    }

    default PriorityListenable<Consumer<T>> removeAddListener(Consumer<T> listener) {
        getAddListenable().remove(listener);
        return null;
    }

}
