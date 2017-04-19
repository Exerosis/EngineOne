package me.engineone.core.completeable;

import me.engineone.core.component.Component;

import java.util.HashSet;
import java.util.Set;

public class Phase extends Component implements Completeable {
    private final Set<Runnable> completeListeners = new HashSet<>();
    private boolean complete = false;

    public Runnable onComplete(Runnable listener) {
        if (completeListeners.contains(listener))
            completeListeners.remove(listener);
        else
            completeListeners.add(listener);
        return listener;
    }

    public Set<Runnable> getCompleteListeners() {
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
        complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}