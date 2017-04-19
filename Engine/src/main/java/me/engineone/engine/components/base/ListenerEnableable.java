package me.engineone.engine.components.base;

import me.engineone.core.enableable.Enableable;
import me.engineone.engine.utilites.EventManager;
import org.bukkit.event.Listener;

public interface ListenerEnableable extends Enableable, EventManager, Listener {

    @Override
    default void enable() {
        registerListener(this);
    }

    @Override
    default void disable() {
        unregisterListener(this);
    }

}
