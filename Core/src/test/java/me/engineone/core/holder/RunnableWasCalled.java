package me.engineone.core.holder;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class RunnableWasCalled extends RunnableCallCounter {
    public boolean wasCalled() {
        return count() > 0;
    }
}
