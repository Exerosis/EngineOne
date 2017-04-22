package me.engineone.core.mutable;

public interface Reducible<T> extends RemoveListenable<T> {
    boolean remove(Object element);

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
