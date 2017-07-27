package me.engineone.engine.holder;

import me.engineone.core.component.CollectionHolderComponent;
import me.engineone.engine.components.event.EventComponent;
import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.function.Predicate;

/**
 * Created by BinaryBench on 5/3/2017.
 */
public class WorldPlayerHolder extends CollectionHolderComponent<Player> {

    public WorldPlayerHolder(Predicate<World> worldPredicate) {

        onEnable(() -> Bukkit.getOnlinePlayers().forEach(player -> {
            if (worldPredicate.test(player.getWorld()))
                if (!contains(player))
                    add(player);
        }));

        addChild(EventComponent.listen(PlayerJoinEvent.class, EventPriority.HIGH, event -> {
            if (worldPredicate.test(event.getPlayer().getWorld()))
                if (!contains(event.getPlayer())) {
                    add(event.getPlayer());
                    System.err.println("Join Add");
                }
        }));

        addChild(EventComponent.listen(PlayerQuitEvent.class, EventPriority.HIGH, event -> {
            if (worldPredicate.test(event.getPlayer().getWorld()))
                if (contains(event.getPlayer())) {
                    remove(event.getPlayer());
                    System.err.println("Quit Remove");
                }
        }));

        addChild(EventComponent.listen(PlayerTeleportEvent.class, EventPriority.HIGH, event -> {

            if (worldPredicate.test(event.getTo().getWorld())) {
                if (!contains(event.getPlayer()))
                    add(event.getPlayer());
            } else {
                if (contains(event.getPlayer()))
                    remove(event.getPlayer());
            }

        }));
    }
}