package me.engineone.engine;

import me.engineone.core.Named;
import me.engineone.core.completeable.ParentPhase;

public class Arena extends ParentPhase implements Named {
    @Override
    public String getName() {
        return "Unnamed Arena";
    }
}
