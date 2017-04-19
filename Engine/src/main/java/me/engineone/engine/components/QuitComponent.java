package me.engineone.engine.components;

import javafx.util.Pair;
import me.engineone.core.mutable.Mutable;
import me.engineone.engine.components.base.ListenerHolderComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Predicate;

public class QuitComponent extends ListenerHolderComponent<Pair<Predicate<Player>, Mutable<Player>>> {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for (Pair<Predicate<Player>, Mutable<Player>> pair : getContents())
            if (pair.getKey().test(event.getPlayer()))
                pair.getValue().add(event.getPlayer());
    }
}