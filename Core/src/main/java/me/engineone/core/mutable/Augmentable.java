package me.engineone.core.mutable;


public interface Augmentable<T> extends AddListenable<T> {
    boolean addSilently(T element);

    default boolean add(T element) {
        getAddListeners().forEach(tConsumer -> tConsumer.accept(element));
        boolean result = addSilently(element);
        getAddedListeners().forEach(tConsumer -> tConsumer.accept(element));
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
