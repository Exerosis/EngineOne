package me.engineone.engine.utilites;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public interface EventManager {
    default void registerListener(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener, Bukkit.getPluginManager().getPlugins()[0]);
    }

    default void unregisterListener(Listener listener){
        HandlerList.unregisterAll(listener);
    }

    default <T extends Event> void callEvent(T event, Consumer<T> callback) {
        callEvent(event);
        callback.accept(event);
    }

    default void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
