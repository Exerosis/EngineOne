package me.engineone.core.holder;

import me.engineone.core.holder.liveholders.MutateListenableDifferenceHolder;
import me.engineone.core.holder.liveholders.MutateListenableUnionHolder;
import me.engineone.core.listenable.Listenable;
import me.engineone.core.mutable.MutateListenable;
import org.javatuples.Pair;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface MutateListenableHolder<T> extends Holder<T>, MutateListenable<T> {

    default MutablePartitionHolder<T> partition(Pair<Predicate<T>, Listenable<Consumer<T>>> filter) {
        return new MutablePartitionHolder<T>(this, filter);
    }

    default MutablePartitionHolder<T> partition(Predicate<T> filter, Listenable<Consumer<T>> updater) {
        return new MutablePartitionHolder<>(this, filter, updater);
    }

    @Override
    default MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return new MutablePartitionHolder<>(this, filter);
    }

    @Override
    default MutateListenableDifferenceHolder<T> difference(Holder<T> holder) {
        return new MutateListenableDifferenceHolder<>(this, holder);
    }

    @Override
    default MutateListenableUnionHolder<T> union(Holder<T> holder) {
        return new MutateListenableUnionHolder<>(this, holder);
    }
}