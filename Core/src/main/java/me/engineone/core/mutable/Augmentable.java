package me.engineone.core.mutable;


public interface Augmentable<T> extends AddListenable<T> {
    boolean add(T element);

    default boolean add(Iterable<? extends T> elements) {
        boolean added = false;
        for (T element : elements)
            added = added | add(element);
        return added;
    }

    @SuppressWarnings("unchecked")
    default boolean add(T... elements) {
        boolean added = false;
        for (T element : elements)
            added = added | add(element);
        return added;
    }

}
