package me.engineone.core.mutable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface MutateListenable<T> extends AddListenable<T>, RemoveListenable<T> {

    @Override
    default MutateListenable<T> onAdd(Consumer<T> listener) {
        return (MutateListenable<T>) AddListenable.super.onAdd(listener);
    }

    @Override
    default MutateListenable<T> onAdded(Consumer<T> listener) {
        return (MutateListenable<T>) AddListenable.super.onAdded(listener);
    }

    @Override
    default MutateListenable<T> onRemove(Consumer<T> listener) {
        return (MutateListenable<T>) RemoveListenable.super.onRemove(listener);
    }

    @Override
    default MutateListenable<T> onRemoved(Consumer<T> listener) {
        return (MutateListenable<T>) RemoveListenable.super.onRemoved(listener);
    }

    @Override
    default MutateListenable<T> unregisterAdd(Consumer<T> listener) {
        return (MutateListenable<T>) AddListenable.super.unregisterAdd(listener);
    }

    @Override
    default MutateListenable<T> unregisterRemove(Consumer<T> listener) {
        return (MutateListenable<T>) RemoveListenable.super.unregisterRemove(listener);
    }
}
