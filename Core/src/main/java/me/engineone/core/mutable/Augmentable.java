package me.engineone.core.mutable;


import com.google.common.collect.ImmutableList;

public interface Augmentable<T> extends AddListenable<T> {
    boolean addSilently(T element);

    default boolean add(T element) {
        ImmutableList.copyOf(getAddListeners()).forEach(tConsumer -> tConsumer.accept(element));
        boolean result = addSilently(element);
        ImmutableList.copyOf(getAddedListeners()).forEach(tConsumer -> tConsumer.accept(element));
        return result;
    }

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
