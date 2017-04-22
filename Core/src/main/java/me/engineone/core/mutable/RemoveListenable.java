package me.engineone.core.mutable;

import me.engineone.core.listenable.PriorityEventListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface RemoveListenable<T> {
    PriorityEventListenable<T> getRemoveListenable();

    default PriorityListenable<Consumer<T>> addRemoveListener(Consumer<T> listener) {
        return getRemoveListenable().add(listener);
    }
    default PriorityListenable<Consumer<T>> addRemoveListener(Consumer<T> listener, float priority) {
        return getRemoveListenable().add(listener, priority);
    }

    default PriorityListenable<Consumer<T>> removeRemoveListener(Consumer<T> listener) {
        return getRemoveListenable().remove(listener);
    }
}
