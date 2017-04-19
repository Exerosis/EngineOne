package me.engineone.engine.predicate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class OnlinePlayerPredicate implements Predicate<Player> {

    @Override
    public boolean test(Player player) {
        return Bukkit.getOnlinePlayers().contains(player);
    }

}