package me.engineone.engine.components.base;

import me.engineone.core.component.CollectionHolderComponent;

import java.util.Collection;

public class ListenerHolderComponent<T> extends CollectionHolderComponent<T> implements ListenerEnableable {
    public ListenerHolderComponent() {
    }

    public ListenerHolderComponent(Collection<T> contents) {
        super(contents);
    }

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