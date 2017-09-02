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
    default ParentEnableable enable() {
        getChildren().forEach(Enableable::enable);
        return this;
    }

    @Override
    default ParentEnableable disable() {
        getChildren().forEach(Enableable::disable);
        return this;
    }
}