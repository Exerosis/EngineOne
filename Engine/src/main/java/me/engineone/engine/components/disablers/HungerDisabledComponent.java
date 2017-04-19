package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.function.Predicate;

public class HungerDisabledComponent extends ListenerComponent {

    private final Predicate<Player> players;
    private final Predicate<FoodLevelChangeEvent> events;

    public HungerDisabledComponent() {
        this(player -> true);
    }

    public HungerDisabledComponent(Predicate<Player> players) {
        this(players, event -> true);
    }

    public HungerDisabledComponent(Predicate<Player> players, Predicate<FoodLevelChangeEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!event.isCancelled())
            event.setCancelled(event.getEntity() instanceof Player && players.test((Player) event.getEntity()) && events.test(event));
    }

}
