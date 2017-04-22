package me.engineone.core.holder;

import com.sun.deploy.net.socket.UnixDomainSocketException;
import me.engineone.core.listenable.PriorityEventListenable;
import me.engineone.core.mutable.Reducible;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

public interface CollectionHolder<T> extends MutableHolder<T>, Collection<T> {

    Collection<T> getContents();
    PriorityEventListenable<T> getAddListenable();
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
        getAddListenable().accept(element);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    default boolean remove(Object element) {
        boolean result = getContents().remove(element);
        getRemoveListenable().accept((T) element);
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
        throw new UnsupportedOperationException("removeAll not yet supported!");
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