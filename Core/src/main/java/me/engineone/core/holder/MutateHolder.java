package me.engineone.core.holder;

import me.engineone.core.holder.liveholders.MutateDifferenceHolder;
import me.engineone.core.holder.liveholders.MutateUnionHolder;
import me.engineone.core.mutable.MutateListenable;

import java.util.function.Predicate;

public interface MutateHolder<T> extends Holder<T>, MutateListenable<T> {

    @Override
    default MutablePartitionHolder<T> partition(Predicate<T> filter) {
        return new MutablePartitionHolder<>(this, filter);
    }

    @Override
    default MutateHolder<T> difference(Holder<T> holder) {
        return new MutateDifferenceHolder<>(this, holder);
    }

    @Override
    default MutateHolder<T> union(Holder<T> holder) {
        return new MutateUnionHolder<>(this, holder);
    }

}