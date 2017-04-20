package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface EventListenable<T> extends Consumer<T>, Listenable<Consumer<T>> {
}
