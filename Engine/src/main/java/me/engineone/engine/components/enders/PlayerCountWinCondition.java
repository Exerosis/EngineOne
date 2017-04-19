package me.engineone.engine.components.enders;

import me.engineone.core.holder.CollectionHolder;
import me.engineone.engine.components.base.ListenerComponent;
import me.engineone.engine.components.death.event.PlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class PlayerCountWinCondition extends ListenerComponent {

    private final CollectionHolder<Player> players;
    private final int count;
    private final Runnable action;

    public PlayerCountWinCondition(CollectionHolder<Player> players, int count, Runnable action) {
        this.players = players;
        this.count = count;
        this.action = action;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (players.test(event.getPlayer()) && players.size() <= count) {
            action.run();
        }
    }
}
