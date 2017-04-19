package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.function.Predicate;

public class BlockBreakDisabledComponent extends ListenerComponent {
    private final Predicate<Player> players;
    private final Predicate<BlockBreakEvent> events;

    public BlockBreakDisabledComponent() {
        this(player -> true);
    }

    public BlockBreakDisabledComponent(Predicate<Player> players) {
        this(players, event -> true);
    }

    public BlockBreakDisabledComponent(Predicate<Player> players, Predicate<BlockBreakEvent> events) {
        this.players = players;
        this.events = events;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) event.setCancelled(players.test(event.getPlayer()) && events.test(event));
    }

}