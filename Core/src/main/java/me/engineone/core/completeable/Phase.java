package me.engineone.core.completeable;

import me.engineone.core.component.Component;

import java.util.ArrayList;
import java.util.List;

public class Phase extends Component implements Completeable {
    private final List<Runnable> completeListeners = new ArrayList<>();
    private boolean complete = false;

    public Phase onComplete(Runnable runnable) {
        getCompleteListeners().add(runnable);
        return this;
    }

    public Phase unregisterComplete(Runnable runnable) {
        getCompleteListeners().add(runnable);
        return this;
    }

    public List<Runnable> getCompleteListeners() {
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
        getCompleteListeners().forEach(Runnable::run);
        if (!isEnabled())
            complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}