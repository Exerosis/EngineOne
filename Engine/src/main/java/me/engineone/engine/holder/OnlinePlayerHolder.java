package me.engineone.engine.holder;

import me.engineone.engine.utilites.EventManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnlinePlayerHolder extends PlayerHolder implements EventManager, Listener {
    public OnlinePlayerHolder() {
        registerListener(this);
    }

    public void unregister() {
        unregisterListener(this);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        add(event.getPlayer());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        remove(event.getPlayer());
    }
}