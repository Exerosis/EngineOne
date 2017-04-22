package me.engineone.core.mutable;

import me.engineone.core.listenable.PriorityEventListenable;
import me.engineone.core.listenable.PriorityListenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface AddListenable<T> {

    PriorityEventListenable<T> getAddListenable();
    
    default PriorityListenable<Consumer<T>> addAddListener(Consumer<T> listener) {
        return getAddListenable().add(listener);
    }
    default PriorityListenable<Consumer<T>> addAddListener(Consumer<T> listener, float priority) {
        return getAddListenable().add(listener, priority);
    }

    default PriorityListenable<Consumer<T>> removeAddListener(Consumer<T> listener) {
        return getAddListenable().remove(listener);
    }
}
