package me.engineone.core.mutable;

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

    Set<Consumer<T>> getRemoveListeners();

    default Consumer<T> onRemove(Consumer<T> listener) {
        if (getRemoveListeners().contains(listener))
            getRemoveListeners().remove(listener);
        else
            getRemoveListeners().add(listener);
        return listener;
    }
}
