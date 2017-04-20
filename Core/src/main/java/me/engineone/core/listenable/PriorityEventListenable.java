package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface PriorityEventListenable<T> extends EventListenable<T>, PriorityListenable<Consumer<T>> {

}
