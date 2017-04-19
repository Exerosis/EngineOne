package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.function.Predicate;

public class DropItemDisabledComponent extends ListenerComponent {

    private final Predicate<Player> players;
    private final Predicate<PlayerDropItemEvent> events;

    public DropItemDisabledComponent() {
        this(player -> true);
    }

    public DropItemDisabledComponent(Predicate<Player> players) {
        this(players, item -> true);
    }

    public DropItemDisabledComponent(Predicate<Player> players, Predicate<PlayerDropItemEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!event.isCancelled())
            event.setCancelled(players.test(event.getPlayer()) && events.test(event));
    }
}