package me.engineone.engine.utilites;

import me.engineone.core.holder.Holder;
import me.engineone.core.holder.PartitionHolder;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public final class PartitionUtilities {
    private PartitionUtilities() {
    }

    public static <T> Pair<PartitionHolder<T>, PartitionHolder<T>> splitPartition(Collection<T> list, Predicate<T> split) {
        Holder<T> parent = new Holder<T>() {
            @Override
            public int size() {
                return list.size();
            }

            @Override
            public Iterator<T> iterator() {
                return list.iterator();
            }

            @Override
            public boolean test(T element) {
                return list.contains(element);
            }
        };
        return Pair.with(new PartitionHolder<T>() {
            @Override
            public Predicate<T> getFilter() {
                return split;
            }

            @Override
            public Holder<T> getParent() {
                return parent;
            }
        }, new PartitionHolder<T>() {
            @Override
            public Predicate<T> getFilter() {
                return split.negate();
            }

            @Override
            public Holder<T> getParent() {
                return parent;
            }
        });
    }

    public static <T> PartitionHolder<T> partition(Predicate<T> filter, T... elements) {
        return partition(Arrays.asList(elements), filter);
    }

    public static <T> PartitionHolder<T> partition(Collection<T> list, Predicate<T> filter) {
        return new Holder<T>() {
            @Override
            public int size() {
                return list.size();
            }

            @Override
            public Iterator<T> iterator() {
                return list.iterator();
            }

            @Override
            public boolean test(T element) {
                return list.contains(element);
            }
        }.partition(filter);
    }
}