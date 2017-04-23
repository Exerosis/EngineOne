package me.engineone.core.holder.helpers;

import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public abstract class MutateListenableList<T> implements List<T> {


    @Override
    public int size() {
        throw new UnsupportedOperationException();
        //return getWrapped().size();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
        //return getWrapped().contains();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
        //return getWrapped().isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
        //return getWrapped().iterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
        //return getWrapped().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
        //return getWrapped().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean flag = false;
        for (Object o : c) {
            flag = flag | contains(o);
        }
        return flag;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean flag = false;
        for (T o : c) {
            flag = flag | add(o);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
        //return getWrapped().addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        for (Object o : c) {
            flag = flag | remove(o);
        }
        return flag;
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
        throw new UnsupportedOperationException();
        //getWrapped().clear();
    }


    @Override
    public T get(int index) {
        throw new UnsupportedOperationException();
        //return getWrapped().get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
        //return getWrapped().set(index, element);
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
        //getWrapped().add(index, element);
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
        //return getWrapped().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
        //return getWrapped().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
        //return getWrapped().lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
        //return getWrapped().listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
        //return getWrapped().listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
        //return getWrapped().subList(fromIndex, toIndex);
    }

}
