package me.engineone.core.mutable;

import me.engineone.core.listenable.Listenable;

import java.util.Set;
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

    Listenable<T> getRemoveListenable();

    default void addRemoveListener(Consumer<T> listener) {
        getRemoveListenable().addListener(listener);
    }
    default void removeRemoveListener(Consumer<T> listener) {
        getRemoveListenable().removeListener(listener);
    }
}
