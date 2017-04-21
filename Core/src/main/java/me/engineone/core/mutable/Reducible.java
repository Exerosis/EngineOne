package me.engineone.core.mutable;

import me.engineone.core.listenable.EventListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.function.Consumer;

public interface Reducible<T> {
    boolean remove(Object element);

    default boolean remove(Iterable<Object> elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed || remove(element);
        return removed;
    }

    default boolean remove(Object[] elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed || remove(element);
        return removed;
    }

    EventListenable<T> getRemoveListenable();

    default PriorityListenable<Consumer<T>> addRemoveListener(Consumer<T> listener) {
        getRemoveListenable().addListener(listener);
        return null;
    }
    default PriorityListenable<Consumer<T>> removeRemoveListener(Consumer<T> listener) {
        getRemoveListenable().removeListener(listener);
        return null;
    }
}
