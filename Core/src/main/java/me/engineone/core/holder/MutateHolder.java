package me.engineone.core.holder;

import me.engineone.core.holder.liveholders.MutateDifferenceHolder;
import me.engineone.core.holder.liveholders.MutateOperatorHolder;
import me.engineone.core.holder.liveholders.MutateUnionHolder;
import me.engineone.core.mutable.MutateListenable;

import java.util.function.Predicate;

public interface MutateHolder<T> extends Holder<T>, MutateListenable<T> {

    @Override
    default MutateHolder<T> union(Holder<T> holder) {
        return new MutateOperatorHolder<>(this, holder, (inPrimary, inSecondary) -> inPrimary || inSecondary);
    }

    @Override
    default Holder<T> intersection(Holder<T> holder) {
        return new MutateOperatorHolder<>(this, holder, (inPrimary, inSecondary) -> inPrimary && inSecondary);
    }

    @Override
    default MutateHolder<T> difference(Holder<T> holder) {
        return new MutateOperatorHolder<>(this, holder, (inPrimary, inSecondary) -> inPrimary && !inSecondary);
    }
}