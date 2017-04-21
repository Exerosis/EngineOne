package me.engineone.core.completeable;

import me.engineone.core.component.Component;
import me.engineone.core.listenable.*;

import java.util.HashSet;
import java.util.Set;

public class Phase extends Component implements Completeable, PriorityListenable<Runnable> {
    private final PriorityRunnableListenable completeListeners = new BasicPriorityRunnableListenable();
    private boolean complete = false;

    @Override
    public boolean isRegistered(Runnable listener) {
        return getCompleteListeners().isRegistered(listener);
    }

    @Override
    public Phase add(Runnable listener) {
        return (Phase) PriorityListenable.super.add(listener);
    }

    @Override
    public Phase add(Runnable listener, float priority) {
        getCompleteListeners().add(listener, priority);
        return this;
    }

    @Override
    public Phase remove(Runnable listener) {
        getCompleteListeners().remove(listener);
        return this;
    }

    @Override
    public float getPriority(Runnable listener) {
        return getCompleteListeners().getPriority(listener);
    }

    public PriorityRunnableListenable getCompleteListeners() {
        return completeListeners;
    }

    @Override
    public void enable() {
        if (!isEnabled())
            complete = false;
        super.enable();
    }

    @Override
    public void complete() {
        if (complete || !isEnabled())
            return;
        getCompleteListeners().run();
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}