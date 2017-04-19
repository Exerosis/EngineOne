package me.engineone.engine.components;

import me.engineone.core.mutable.Mutable;
import me.engineone.engine.components.base.ListenerHolderComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.javatuples.Triplet;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class JoinComponent extends ListenerHolderComponent<Triplet<Predicate<Player>, Mutable<Player>, Consumer<Player>>> {
    private final Consumer<Player> none;

    public JoinComponent(Consumer<Player> none) {
        this.none = none;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Triplet<Predicate<Player>, Mutable<Player>, Consumer<Player>> tuple : getContents()) {
            if (tuple.getValue0().test(player)) {
                tuple.getValue1().add(player);
                tuple.getValue2().accept(player);
                return;
            }
        }
        // none.biConsumer(player);
    }
}