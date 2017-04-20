package me.engineone.core.listenable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by BinaryBench on 4/20/2017.
 */
public interface Listenable {
    void addListener(Runnable listener);
    void removeListener(Runnable listener);
    boolean isRegistered(Runnable listener);
    void call();
}
