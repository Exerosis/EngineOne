package me.engineone.core.enableable;

import me.engineone.core.Parent;

public interface ParentEnableable extends Enableable, Parent<Enableable> {
    @Override
    default void enable() {
        getChildren().forEach(Enableable::enable);
    }

    @Override
    default void disable() {
        getChildren().forEach(Enableable::disable);
    }
}