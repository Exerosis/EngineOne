package me.engineone.engine.holder.event;

import me.engineone.engine.holder.PlayerHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerHolderEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final PlayerHolder holder;
    private final Player player;

    public PlayerHolderEvent(PlayerHolder holder, Player player) {
        this.holder = holder;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public PlayerHolder getHolder() {
        return holder;
    }

    public Player getPlayer() {
        return player;
    }

}
