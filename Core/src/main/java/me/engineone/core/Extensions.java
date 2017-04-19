package me.engineone.core;

import com.google.common.collect.Iterables;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Extensions {

    //--Method Reference--
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }


    //--Bundle--
    public static <A, B> Pair<A, B> bundle(A first, B second) {
        return new Pair<>(first, second);
    }

    public static <A, B, C> Triplet<A, B, C> bundle(A first, B second, C third) {
        return new Triplet<>(first, second, third);
    }


    //--Runnable--
    public static <T> Runnable runnable(Consumer<T> consumer, T first) {
        return () -> consumer.accept(first);
    }

    public static <T> Runnable runnable(Supplier<T> first, Consumer<T> consumer) {
        return () -> consumer.accept(first.get());
    }

    public static <A, B> Runnable runnable(BiConsumer<A, B> consumer, A first, B second) {
        return () -> consumer.accept(first, second);
    }

    public static <A, B> Runnable runnable(BiConsumer<A, B> consumer, A first, B second, Runnable... runnables) {
        return () -> {
            forEach(Runnable::run, runnables);
            consumer.accept(first, second);
        };
    }


    //--Consumer--
    public static <T> Consumer<T> consumer(Runnable runnable) {
        return (first) -> runnable.run();
    }

    public static <T> Consumer<T> consumer(Consumer<T> consumer, Predicate<T> filter) {
        return param -> {
            if (filter.test(param))
                consumer.accept(param);
        };
    }

    public static <A, B> Consumer<A> biConsumer(BiConsumer<A, B> consumer, B second) {
        return first -> consumer.accept(first, second);
    }

    public static <A, B> Consumer<B> consumer(A second, Consumer<A> consumer) {
        return first -> consumer.accept(second);
    }

/*    public static <A, R> Consumer<A> biConsumer(Function<A, R> function) {
        return function::apply;
    }

    public static <A, B, R> Consumer<A> biConsumer(BiFunction<A, B, R> function, B second) {
        return first -> function.apply(first, second);
    }*/


    //--Function--
    public static <A, B, R> Function<A, R> function(BiFunction<A, B, R> function, B second) {
        return first -> function.apply(first, second);
    }

    //--Predicate
    public static <A, B> Predicate<A> predicate(BiPredicate<A, B> predicate, B second) {
        return first -> predicate.test(first, second);
    }

    public static <A, B> Predicate<A> predicate(Predicate<B> predicate, Function<A, B> converter) {
        return param -> predicate.test(converter.apply(param));
    }

    //--Chain--
    public static Runnable chain(Runnable... runnables) {
        return () -> forEach(Runnable::run, runnables);
    }

    @SafeVarargs
    public static <T> Consumer<T> chain(Consumer<T>... consumers) {
        return param -> forEach(consumer -> consumer.accept(param), consumers);
    }

    //--Utilities
    public static void range(int min, int max, Consumer<Integer> consumer) {
        for (int i = min; i <= max; i++)
            consumer.accept(i);
    }

    public static void range(int max, Consumer<Integer> consumer) {
        range(0, max, consumer);
    }

    public static <A, B> Consumer<A> convert(Consumer<B> consumer, Function<A, B> converter) {
        return first -> consumer.accept(converter.apply(first));
    }

    @SuppressWarnings("unchecked")
    public static <A, B> Consumer<A> cast(Consumer<B> consumer) {
        return param -> consumer.accept((B) param);
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> concatStream(Iterable<? extends Iterable<T>> iterables) {
        return StreamSupport.stream(Iterables.concat(iterables).spliterator(), false);
    }

    @SafeVarargs
    public static <T> Iterable<T> iterate(T... array) {
        return () -> new Iterator<T>() {
            private int index = 0;
            private T next = getNext();
            private boolean done = false;

            @Override
            public boolean hasNext() {
                return !done;
            }

            @Override
            public T next() {
                if (done)
                    throw new NoSuchElementException();
                return next;
            }

            private T getNext() {
                if (array.length > index)
                    return array[index++];
                else
                    done = true;
                return null;
            }
        };
    }

    @SafeVarargs
    public static <T> void forEach(Consumer<T> consumer, T... array) {
        for (T element : array)
            consumer.accept(element);
    }

    public static <T> void forEach(Iterable<T> iterable, Consumer<T> consumer) {
        iterable.forEach(consumer);
    }

    public static <T> List<T> list(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    public static <T> Set<T> set(Stream<T> stream) {
        return stream.collect(Collectors.toSet());
    }
}