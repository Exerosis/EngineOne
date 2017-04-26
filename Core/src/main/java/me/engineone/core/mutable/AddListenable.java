package me.engineone.core.mutable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public interface AddListenable<T> {

    List<Consumer<T>> getAddListeners();
    List<Consumer<T>> getAddedListeners();
    
    default AddListenable<T> onAdd(Consumer<T> listener) {
        getAddListeners().add(listener);
        return this;
    }

    default AddListenable<T> unregisterAdd(Consumer<T> listener) {
        getAddListeners().remove(listener);
        return this;
    }

    default AddListenable<T> onAdded(Consumer<T> listener) {
        getAddedListeners().add(listener);
        return this;
    }

    default AddListenable<T> unregisterAdded(Consumer<T> listener) {
        getAddedListeners().remove(listener);
        return this;
    }
}
