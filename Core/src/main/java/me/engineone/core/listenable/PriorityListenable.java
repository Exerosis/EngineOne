package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityListenable extends Listenable {

    default void addListener(Runnable listener) {
        addListener(listener, 1);
    }

    void removeListener(Runnable listener);

    boolean isRegistered(Runnable listener);

    void addListener(Runnable listener, float priority);

    float getPriority(Runnable listener);
}
