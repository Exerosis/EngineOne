package me.engineone.engine.components.base;

import me.engineone.core.component.ParentComponent;

public class ParentListenerComponent extends ParentComponent implements ListenerEnableable {
    @Override
    public void enable() {
        super.enable();
        ListenerEnableable.super.enable();
    }

    @Override
    public void disable() {
        super.disable();
        ListenerEnableable.super.disable();
    }

}