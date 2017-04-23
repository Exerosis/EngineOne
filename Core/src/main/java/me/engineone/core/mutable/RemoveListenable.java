package me.engineone.core.mutable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface RemoveListenable<T> {
    List<Consumer<T>> getRemoveListeners();

    default RemoveListenable<T> onRemove(Consumer<T> listener) {
        getRemoveListeners().add(listener);
        return this;
    }
    default RemoveListenable<T> unregisterRemove(Consumer<T> listener) {
        getRemoveListeners().remove(listener);
        return this;
    }
}
