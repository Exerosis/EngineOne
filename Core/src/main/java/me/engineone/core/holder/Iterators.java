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

    public static <T> Iterator<T> union(Iterator<T> primary, Iterator<T> secondary) {
        return new Iterator<T>() {
            private T next;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                if (this.next == null)
                    return null;
                T next = this.next;
                this.next = primary.hasNext() ? primary.next() : secondary.hasNext() ? secondary.next() : null;
                return next;
            }
        };
    }

    public static <T> Iterator<T> difference(Iterator<T> primary, Predicate<T> secondary) {
        return new Iterator<T>() {
            private final Iterator<T> iterator = primary;
            private T next = iterator.hasNext() ? iterator.next() : null;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public T next() {
                T next = this.next;
                do {
                    if (iterator.hasNext())
                        this.next = iterator.next();
                    else {
                        this.next = null;
                        break;
                    }
                } while (secondary.test(this.next));
                return next;
            }
        };
    }
}
