package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class MovementDisabledComponent extends ListenerComponent {

    private final Predicate<Player> players;
    private final Predicate<Location> locations;

    public MovementDisabledComponent() {
        this(player -> true);
    }

    public MovementDisabledComponent(Predicate<Player> players) {
        this(players, location -> true);
    }

    public MovementDisabledComponent(Predicate<Player> players, Predicate<Location> locations) {
        this.players = players;
        this.locations = locations;
    }

}