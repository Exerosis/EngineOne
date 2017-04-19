package me.engineone.engine.components.disablers;

import me.engineone.engine.components.base.ListenerComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.function.Predicate;

public class PvPDisabledComponent extends ListenerComponent {
    private final Predicate<Player> players;
    private final Predicate<Player> attackers;

    public PvPDisabledComponent() {
        this(player -> true);
    }

    public PvPDisabledComponent(Predicate<Player> players) {
        this(players, attacker -> true);
    }

    public PvPDisabledComponent(Predicate<Player> players, Predicate<Player> attackers) {
        this.players = players;
        this.attackers = attackers;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        event.setCancelled(players.test(player) && attackers.test(attacker));
        if (event.isCancelled())
            Bukkit.broadcastMessage(player.getName() + " </- " + attacker.getName());
        else
            Bukkit.broadcastMessage(player.getName() + " <- " + attacker.getName());
    }
}