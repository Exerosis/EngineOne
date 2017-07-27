package me.engineone.engine.holder;

import me.engineone.core.holder.BasicCollectionHolder;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnlinePlayerHolder extends BasicCollectionHolder<Player> implements Listener {
    public OnlinePlayerHolder() {
        ServerUtil.registerListener(this);
    }

    public void unregister() {
        ServerUtil.unregisterListener(this);
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