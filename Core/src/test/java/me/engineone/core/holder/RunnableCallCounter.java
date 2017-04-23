package me.engineone.core.holder;

/**
 * Created by BinaryBench on 4/22/2017.
 */
public class RunnableCallCounter implements Runnable {
    private int counter = 0;

    @Override
    public void run() {
        counter++;
    }

    public int count() {
        return counter;
    }

    public void reset() {
        counter = 0;
    }
}
