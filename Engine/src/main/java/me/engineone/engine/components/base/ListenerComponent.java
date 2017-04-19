package me.engineone.engine.components.base;

import me.engineone.core.component.Component;

public class ListenerComponent extends Component implements ListenerEnableable {
    @Override
    public void enable() {
        ListenerEnableable.super.enable();
        super.enable();
    }


    @Override
    public void disable() {
        ListenerEnableable.super.disable();
        super.disable();
    }
}