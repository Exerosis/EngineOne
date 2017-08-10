package me.engineone.engine.components.countdown;

import me.engineone.core.component.AddToListComponent;
import me.engineone.core.holder.MutateHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created by BinaryBench on 5/23/2017.
 */
public class HolderCountCountdownPhase extends CountdownPhase {

    private List<BiConsumer<Integer, Integer>> waitingListeners = new ArrayList<>();

    private int startThreshold;
    private int stopThreshold;
    private MutateHolder<?> holder;

    public HolderCountCountdownPhase(ScheduledExecutorService scheduler, int count, int startThreshold, int stopThreshold, MutateHolder<?> holder) {
        this(scheduler, count, 1, TimeUnit.SECONDS, startThreshold, stopThreshold, holder);
    }

    public HolderCountCountdownPhase(ScheduledExecutorService scheduler, int count, long period, TimeUnit timeUnit, int startThreshold, int stopThreshold, MutateHolder<?> holder) {
        super(scheduler, count, period, timeUnit);
        this.startThreshold = startThreshold;
        this.stopThreshold = stopThreshold;
        this.holder = holder;

        //FIX ME
//        getEnableListeners().clear();

        addChild(new AddToListComponent<>(holder.getAddedListeners(), o -> checkCountdown()));
        addChild(new AddToListComponent<>(holder.getRemovedListeners(), o -> checkCountdown()));

        onEnable(this::checkCountdown);
    }

    public void checkCountdown() {
        int holderCount = holder.size();
        if (holderCount >= this.startThreshold) {
            if (!isRunning()) {
                stop();
                reset();
                start();
            }
        }
        else if (holderCount < this.stopThreshold) {
            if (isRunning())
                stop();

            getWaitingListeners().forEach(listener -> listener.accept(holderCount, this.startThreshold));
        }
    }

    public void onWaiting(BiConsumer<Integer, Integer> listener) {
        getWaitingListeners().add(listener);
    }

    public List<BiConsumer<Integer, Integer>> getWaitingListeners() {
        return waitingListeners;
    }
}
