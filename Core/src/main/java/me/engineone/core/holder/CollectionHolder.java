package me.engineone.core.holder;

import me.engineone.core.listenable.PriorityEventListenable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface CollectionHolder<T> extends MutableHolder<T>, Collection<T> {
    Collection<T> getContents();

    /**
     * Alias for {@link PriorityEventListenable#addListener(Consumer) getAddListenable().addListener(Consumer)}
     *
     * @param listener listener to register
     */
    default void addAddListener(Consumer<T> listener) {
        getAddListenable().addListener(listener);
    }
    /**
     * Alias for {@link PriorityEventListenable#addListener(Consumer, float) getAddListenable().addListener(Consumer, float)}
     *
     * @param listener listener to register
     * @param priority listener priority
     */
    default void addAddListener(Consumer<T> listener, float priority) {
        getAddListenable().addListener(listener, priority);
    }
    /**
     * Alias for {@link PriorityEventListenable#removeListener(Consumer) getAddListenable().removeListener(Consumer)}
     *
     * @param listener listener to register
     */
    default void removeAddListener(Consumer<T> listener) {
        getAddListenable().removeListener(listener);
    }
    PriorityEventListenable<T> getAddListenable();


    /**
     * Alias for {@link PriorityEventListenable#addListener(Consumer) getRemoveListenable().addListener(Consumer)}
     *
     * @param listener listener to register
     */
    default void addRemoveListener(Consumer<T> listener) {
        getRemoveListenable().addListener(listener);
    }
    /**
     * Alias for {@link PriorityEventListenable#addListener(Consumer, float) getRemoveListenable().addListener(Consumer, float)}
     *
     * @param listener listener to register
     * @param priority listener priority
     */
    default void addRemoveListener(Consumer<T> listener, float priority) {
        getRemoveListenable().addListener(listener, priority);
    }
    /**
     * Alias for {@link PriorityEventListenable#removeListener(Consumer) getRemoveListenable().removeListener(Consumer)}
     *
     * @param listener listener to register
     */
    default void removeRemoveListener(Consumer<T> listener) {
        getRemoveListenable().removeListener(listener);
    }
    PriorityEventListenable<T> getRemoveListenable();



    @Override
    default Stream<T> stream() {
        return MutableHolder.super.stream();
    }

    @Override
    default Stream<T> parallelStream() {
        return MutableHolder.super.parallelStream();
    }

    @Override
    default boolean add(T element) {
        boolean result = getContents().add(element);
        getAddListenable().call(element);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    default boolean remove(Object element) {
        boolean result = getContents().remove(element);
        getRemoveListenable().call((T) element);
        return result;
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return getContents().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends T> c) {
        return add(c);
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        return remove(c);
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        return getContents().retainAll(c);
    }

    @Override
    default void clear() {
        getContents().clear();
    }

    @Override
    default boolean test(T element) {
        return getContents().contains(element);
    }

    @Override
    default int size() {
        return getContents().size();
    }

    @Override
    default Spliterator<T> spliterator() {
        return MutableHolder.super.spliterator();
    }

    @Override
    default boolean isEmpty() {
        return getContents().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return getContents().contains(o);
    }

    @Override
    default Object[] toArray() {
        return getContents().toArray();
    }

    @Override
    default <E> E[] toArray(E[] a) {
        return getContents().toArray(a);
    }

    @Override
    default Iterator<T> iterator() {
        return getContents().iterator();
    }
}