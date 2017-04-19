package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.Predicate;

public class PvEDisabledComponent extends ListenerComponent {

    private final Predicate<Player> players;
    private final Predicate<EntityDamageEvent> events;

    public PvEDisabledComponent() {
        this(player -> true);
    }

    public PvEDisabledComponent(Predicate<Player> players) {
        this(players, event -> true);
    }

    public PvEDisabledComponent(Predicate<Player> players, Predicate<EntityDamageEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.isCancelled())
            event.setCancelled(event.getEntity() instanceof Player && players.test((Player) event.getEntity()) && events.test(event));
    }

}
