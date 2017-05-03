package me.engineone.engine.components.base;

import me.engineone.core.enableable.Enableable;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.event.Listener;

public interface ListenerEnableable extends Enableable, Listener {

    @Override
    default void enable() {
        ServerUtil.registerListener(this);
    }

    @Override
    default void disable() {
        ServerUtil.unregisterListener(this);
    }

}
