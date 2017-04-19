package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.function.Predicate;

public class BlockPlaceDisabledComponent extends ListenerComponent {
    private final Predicate<Player> players;
    private final Predicate<BlockPlaceEvent> events;

    public BlockPlaceDisabledComponent() {
        this(player -> true);
    }

    public BlockPlaceDisabledComponent(Predicate<Player> players) {
        this(players, event -> true);
    }

    public BlockPlaceDisabledComponent(Predicate<Player> players, Predicate<BlockPlaceEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!event.isCancelled()) event.setCancelled(players.test(event.getPlayer()) && events.test(event));
    }
}