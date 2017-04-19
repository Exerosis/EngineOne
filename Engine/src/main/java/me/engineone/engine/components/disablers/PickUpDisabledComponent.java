package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.function.Predicate;

public class PickUpDisabledComponent extends ListenerComponent {

    private final Predicate<Player> players;
    private final Predicate<PlayerPickupItemEvent> events;

    public PickUpDisabledComponent() {
        this(player -> true);
    }

    public PickUpDisabledComponent(Predicate<Player> players) {
        this(players, item -> true);
    }

    public PickUpDisabledComponent(Predicate<Player> players, Predicate<PlayerPickupItemEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        if (!event.isCancelled())
            event.setCancelled(players.test(event.getPlayer()) && events.test(event));
    }

}