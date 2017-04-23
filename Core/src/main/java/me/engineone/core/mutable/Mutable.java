package me.engineone.core.mutable;

import java.util.function.Consumer;

public interface Mutable<T> extends MutateListenable<T>, Augmentable<T>, Reducible<T> {

    @Override
    default Mutable<T> onAdd(Consumer<T> listener) {
        return (Mutable<T>) MutateListenable.super.onAdd(listener);
    }

    @Override
    default Mutable<T> onRemove(Consumer<T> listener) {
        return (Mutable<T>) MutateListenable.super.onRemove(listener);
    }

    @Override
    default Mutable<T> unregisterAdd(Consumer<T> listener) {
        return (Mutable<T>) MutateListenable.super.unregisterAdd(listener);
    }

    @Override
    default Mutable<T> unregisterRemove(Consumer<T> listener) {
        return (Mutable<T>) MutateListenable.super.unregisterRemove(listener);
    }

}