package me.engineone.core.holder;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public final class Iterators {
    private Iterators() {
    }

    public static <E> Iterator<E> filter(Iterator<E> iterator, Predicate<E> filter) {
        return new Iterator<E>() {
            private boolean ready = false;
            private E next = getNext();

            @Override
            public boolean hasNext() {
                return ready;
            }

            @Override
            public E next() {
                if (!ready)
                    throw new NoSuchElementException();
                E next = this.next;
                this.next = getNext();
                return next;
            }

            private E getNext() {
                E next = null;
                while ((next == null || !filter.test(next)) && (ready = iterator.hasNext()))
                    next = iterator.next();
                return next;
            }
        };
    }
}
