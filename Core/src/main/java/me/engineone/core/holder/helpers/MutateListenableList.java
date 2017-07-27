package me.engineone.core.holder.helpers;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public abstract class MutateListenableList<T> implements List<T> {

    List<T> wrapped;


    public MutateListenableList() {
        this(new ArrayList<>());
    }

    public MutateListenableList(List<T> wrapped) {
        this.wrapped = wrapped;
    }

    protected abstract void adding(T element);
    protected abstract void adding(int index, T element);

    protected abstract void removing(int index);
    protected abstract void removing(T element);


    @Override
    public int size() {
        return getWrapped().size();
    }

    @Override
    public boolean isEmpty() {
        return getWrapped().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getWrapped().contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return getWrapped().iterator();
    }

    @Override
    public Object[] toArray() {
        return getWrapped().toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return getWrapped().toArray(a);
    }

    @Override
    public boolean add(T t) {
        adding(t);
        return getWrapped().add(t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {

        removing((T) o);
        return getWrapped().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getWrapped().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
        //return getWrapped().addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
        //return getWrapped().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
        //return getWrapped().removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
        //return getWrapped().retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
        //getWrapped().replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        throw new UnsupportedOperationException();
        //getWrapped().sort(c);
    }

    @Override
    public void clear() {
        Iterator<T> iterator = getWrapped().iterator();
        iterator.forEachRemaining(this::remove);

        //throw new UnsupportedOperationException();
        //getWrapped().clear();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return getWrapped().equals(o);
    }

    @Override
    public int hashCode() {
        return getWrapped().hashCode();
    }

    @Override
    public T get(int index) {
        return getWrapped().get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
        //return getWrapped().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        adding(index, element);
        getWrapped().add(index, element);
    }

    @Override
    public T remove(int index) {
        removing(index);
        return getWrapped().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return getWrapped().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getWrapped().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return getWrapped().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return getWrapped().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return getWrapped().subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<T> spliterator() {
        return getWrapped().spliterator();
    }

    public List<T> getWrapped() {
        return wrapped;
    }

}
