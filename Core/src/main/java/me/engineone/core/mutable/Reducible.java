package me.engineone.core.mutable;

public interface Reducible<T> extends RemoveListenable<T> {
    boolean removeSilently(T element);

    @SuppressWarnings("unchecked")
    default boolean remove(Object element) {
        boolean result = removeSilently((T) element);
        if (result)
            getRemoveListenable().accept((T) element);
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