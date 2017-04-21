package me.engineone.core.listenable;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface Listenable<T> {
    Listenable<T> add(T listener);
    Listenable<T> remove(T listener);
    boolean isRegistered(T listener);
}
