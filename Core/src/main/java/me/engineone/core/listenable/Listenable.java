package me.engineone.core.listenable;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface Listenable<T> {
    Listenable<T> addListener(T listener);
    Listenable<T> removeListener(T listener);
    boolean isRegistered(T listener);
}
