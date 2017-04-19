package me.engineone.engine.components;

import me.engineone.engine.components.base.ListenerComponent;
import me.engineone.engine.holder.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Predicate;

public class TeamComponent extends ListenerComponent {
    private final Predicate<Player> players;
    private final Team team;

    public TeamComponent(Predicate<Player> players, Team team) {
        this.players = players;
        this.team = team;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (players.test(event.getPlayer())) team.add(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (players.test(event.getPlayer())) team.remove(event.getPlayer());
    }
}