package me.engineone.core.listenable;

import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/21/2017.
 */
public interface WrapperPriorityEventListenable<T, W extends PriorityListenable<Consumer<T>>> extends WrapperPriorityListenable<Consumer<T>, W> {

}
