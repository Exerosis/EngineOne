package me.engineone.core.mutable;

import com.google.common.collect.ImmutableList;

public interface Reducible<T> extends RemoveListenable<T> {
    boolean removeSilently(T element);

    @SuppressWarnings("unchecked")
    default boolean remove(Object element) {
        ImmutableList.copyOf(getRemoveListeners()).forEach(tConsumer -> tConsumer.accept((T) element));
        boolean result = removeSilently((T) element);
        ImmutableList.copyOf(getRemovedListeners()).forEach(tConsumer -> tConsumer.accept((T) element));
        return result;
    }

    default boolean remove(Iterable<Object> elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed | remove(element);
        return removed;
    }

    default boolean remove(Object[] elements) {
        boolean removed = false;
        for (Object element : elements)
            removed = removed | remove(element);
        return removed;
    }


}