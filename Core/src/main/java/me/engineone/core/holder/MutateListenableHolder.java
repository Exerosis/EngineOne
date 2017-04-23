package me.engineone.core.holder;

import javafx.util.Pair;
import me.engineone.core.holder.liveholders.MutateListenableDifferenceHolder;
import me.engineone.core.holder.liveholders.MutateListenableUnionHolder;
import me.engineone.core.mutable.MutateListenable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface MutateListenableHolder<T> extends Holder<T>, MutateListenable<T> {

    @Override
    default MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return new MutablePartitionHolder<>(this, filter);
    }

    @Override
    default MutateListenableHolder<T> difference(Holder<T> holder) {
        return new MutateListenableDifferenceHolder<>(this, holder);
    }

    @Override
    default MutateListenableHolder<T> union(Holder<T> holder) {
        return new MutateListenableUnionHolder<>(this, holder);
    }

}