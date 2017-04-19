package me.engineone.core.holder;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Holder<T> extends Predicate<T>, Iterable<T> {
    int size();

    default Holder<T> union(Holder<T> holder) {
        return new LiveHolder<T>() {
            @Override
            public Holder<T> getPrimary() {
                return Holder.this;
            }

            @Override
            public Holder<T> getSecondary() {
                return holder;
            }

            @Override
            public boolean test(T element) {
                return getPrimary().test(element) || holder.test(element);
            }

            @Override
            public int size() {
                int size = holder.size();
                for (T t : getPrimary())
                    if (!holder.test(t))
                        size++;
                return size;
            }

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> first = holder.iterator();
                    private final Iterator<T> second = getPrimary().iterator();
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
                        this.next = first.hasNext() ? first.next() : second.hasNext() ? second.next() : null;
                        return next;
                    }
                };
            }
        };
    }

    default LiveHolder<T> difference(Holder<T> holder) {
        return new LiveHolder<T>() {
            @Override
            public Holder<T> getPrimary() {
                return Holder.this;
            }

            @Override
            public Holder<T> getSecondary() {
                return holder;
            }

            @Override
            public boolean test(T element) {
                return !holder.test(element) && getPrimary().test(element);
            }

            @Override
            public int size() {
                int size = getPrimary().size();
                for (T t : getPrimary())
                    if (holder.test(t))
                        size--;
                return size;
            }

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private final Iterator<T> iterator = getPrimary().iterator();
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
                        } while (holder.test(this.next));
                        return next;
                    }
                };
            }
        };
    }

    default PartitionHolder<T> partition(Predicate<T> filter) {
        return new PartitionHolder<T>() {
            @Override
            public Predicate<T> getFilter() {
                return filter;
            }

            @Override
            public Holder<T> getParent() {
                return Holder.this;
            }
        };
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT);
    }

    default Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT), false);
    }

    default Stream<T> parallelStream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT), true);
    }

    @Override
    boolean test(T element);
}