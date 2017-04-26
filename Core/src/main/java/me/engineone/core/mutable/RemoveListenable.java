package me.engineone.core.mutable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface RemoveListenable<T> {
    List<Consumer<T>> getRemoveListeners();
    List<Consumer<T>> getRemovedListeners();

    default RemoveListenable<T> onRemove(Consumer<T> listener) {
        getRemoveListeners().add(listener);
        return this;
    }

    default RemoveListenable<T> onRemoved(Consumer<T> listener) {
        getRemovedListeners().add(listener);
        return this;
    }

    default RemoveListenable<T> unregisterRemove(Consumer<T> listener) {
        getRemoveListeners().remove(listener);
        return this;
    }


    default RemoveListenable<T> unregisterRemoved(Consumer<T> listener) {
        getRemovedListeners().remove(listener);
        return this;
    }
}
