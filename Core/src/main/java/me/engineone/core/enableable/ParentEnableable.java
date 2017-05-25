package me.engineone.core.enableable;

import me.engineone.core.Parent;

public interface ParentEnableable extends Enableable, Parent<Enableable> {

    @Override
    default boolean removeChild(Enableable child) {
        if (isEnabled() && child.isEnabled())
            child.disable();
        return Parent.super.removeChild(child);
    }

    @Override
    default <B extends Enableable> B addChild(B child) {
        if (isEnabled() && !child.isEnabled())
            child.enable();
        return Parent.super.addChild(child);
    }

    @Override
    default void enable() {
        getChildren().forEach(Enableable::enable);
    }

    @Override
    default void disable() {
        getChildren().forEach(Enableable::disable);
    }


}